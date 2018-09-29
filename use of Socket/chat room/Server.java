package cn.aa;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(9100); //创建一个新的服务器socket，并占用端号9100
			Socket socket  = server.accept(); //取得与本socket连接的另一个socket，没有则返回null
			while(true) {
				byte[] b = new byte[2*1024];//创造比特数组
				InputStream is = socket.getInputStream();//取得连接到的socket的输入流
				int length = is.read(b);//length：取得在输入流获得到的字符串的长度；read方法，将在输入流的数据放在比特数组中
				System.out.println(socket.getInetAddress().getHostAddress()+":"+new String(b,0,length)); //输出主机名和从输入流获得到的字符串
				
				String str = new Scanner(System.in).next();
				OutputStream os = socket.getOutputStream(); //创造输出流
				os.write(str.getBytes()); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}