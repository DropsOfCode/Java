package com.example.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		/*
		 * ServerSocket : ������ר���׽���
		 * Socket �� �ͻ����׽��֣�ServerSocket��accpet()�������Խ���
		 * */
		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			ss = new ServerSocket(9100); //���ö˿�9100
			s = ss.accept(); //�ӿͻ��˽����׽���
			while(true) { //����һ��ѭ���� �ﵽ��������д�������
				/*д����*/
				os = s.getOutputStream();
				String str = new Scanner(System.in).next();
				os.write(str.getBytes());
				
				
				/*������*/
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
