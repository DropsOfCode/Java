package com.example.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		/*
		 * Socket : �ͻ�ר���׽���
		 * */
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			/*
			 * ָ�����ӵķ�����ip���˿ںţ�ip��ַ��127.0.0.1��ʾ���Ǳ���ip��ַ
			 * */
			s = new Socket("127.0.0.1", 9100);
			while(true) { //����ѭ��������д����
				
				/*������*/
				is = s.getInputStream();
				byte[] b = new byte[2*1024];
				int length = is.read(b);
				System.out.println(new String(b,0,length));
				
				
				/*д����*/
				os = s.getOutputStream();
				String str = new Scanner(System.in).next();
				os.write(str.getBytes());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
