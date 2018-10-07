package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	ServerSocket ss = null; //服务器端的套接字，用来监听客户端的连接请求
	boolean started = false;
	
	List<Client> clients = new ArrayList<Client>(); //维护一个客户端链表，用于记录已经连接上的用户
	
	public static void main(String[] args) {
		new Server().start();  //调用本类的start方法，因为服务器操作全都写在start方法中
	}
	
	public void start() {
		try {
			ss = new ServerSocket(9100); //指定服务器端口
			started = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			while(started) {
				Socket s = ss.accept(); //创造一个用以管理用户的套接字。每当有新的客户端连接，服务器就新建一个套接字给此用户端
				Client c = new Client(s);//每当客户连接上服务端，就新建一个Client类，指代客户端，并传入客户端套接字
				
				new Thread(c).start(); //新建一个子线程，并启动它
				clients.add(c); //Client对线加入到客户端列表中。
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class Client implements Runnable{

		Socket s = null; //客户端套接字的引用
		DataInputStream dis = null; //输入流的引用
		DataOutputStream dos = null; //输出流的引用
		boolean bConnected = false; //服务端是否接收到了客户端的输入流，输出流
		
		public Client(Socket s) { //Client类的构造函数
			this.s = s; //传入客户端的套接字
			try { 
				dis = new DataInputStream(s.getInputStream()); //getInputStream用于获取客户端套接字自带的输入流
				dos = new DataOutputStream(s.getOutputStream());//getOutputStream用于获取客户端套接字自带的输出流
				bConnected = true; //成功连接上客户端套接字
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/*
		 * 用户客户端发送信息*/
		public void send(String str) {
			try {
				dos.writeUTF(str); //向输入流发送信息
			}catch(IOException e) {
				clients.remove(s); //如果Client对象发生异常，则从Client中删除
			}
		}
		
		public void run() {
			String str = null;
			try {
				while(bConnected) { //当成功获取客户端的输入流，输出流后。
					str = dis.readUTF();  //从输出流读取来自客户端信息的信息
					
					for(int i=0;i<clients.size();i++) { //从一个客户端获取消息之后，发此消息到每个已经连接上服务器的客户端
						Client c = clients.get(i);
						c.send(str);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(s!=null) {
						s.close();
					}
					if(dos!=null) {
						dos.close();
					}
					if(dis!=null) {
						dis.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}


