package com.example.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		/*
		 * Socket : 客户专用套接字
		 * */
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			/*
			 * 指定连接的服务器ip，端口号，ip地址：127.0.0.1表示的是本地ip地址
			 * */
			s = new Socket("127.0.0.1", 9100);
			while(true) { //设置循环反复读写操作
				
				/*读操作*/
				is = s.getInputStream();
				byte[] b = new byte[2*1024];
				int length = is.read(b);
				System.out.println(new String(b,0,length));
				
				
				/*写操作*/
				os = s.getOutputStream();
				String str = new Scanner(System.in).next();
				os.write(str.getBytes());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
