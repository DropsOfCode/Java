package com.example.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		/*
		 * ServerSocket : 服务器专用套接字
		 * Socket ： 客户端套接字，ServerSocket有accpet()方法可以接收
		 * */
		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			ss = new ServerSocket(9100); //设置端口9100
			s = ss.accept(); //从客户端接收套接字
			while(true) { //设置一个循环， 达到反复接收写入的作用
				/*写操作*/
				os = s.getOutputStream();
				String str = new Scanner(System.in).next();
				os.write(str.getBytes());
				
				
				/*读操作*/
				is = s.getInputStream();
				byte[] b = new byte[2*1024];
				int length = is.read(b);
				System.out.println(new String(b,0,length));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
