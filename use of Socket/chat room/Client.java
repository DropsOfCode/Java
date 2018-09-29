package cn.aa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("192.168.2.102", 9100); //与服务器的socket连接
			while(true) { //搭建循环
				OutputStream os = socket.getOutputStream(); //取得连接到的socket的输出流
				String str = new Scanner(System.in).next(); //输入文字
				os.write(str.getBytes()); //将文字输入到输出流中
				os.flush(); //输出流清流
				
				InputStream is = socket.getInputStream(); //取得连接到的socket的输入流
				byte[] b = new byte[2*1024]; //创造比特数组
				int length = is.read(b); //length：取得在输入流获得到的字符串的长度；read方法，将在输入流的数据放在比特数组中
				System.out.print(socket.getInetAddress().getHostName()+":"); //获得主机名，如果防火墙不允许或没有主机名，则返回ip地址
				System.out.println(new String(b,0,length)); // 在荧幕上输出刚才输出的文字
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
