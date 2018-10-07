package test;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ChatClient extends Frame {
	Socket s = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	boolean bConnected = false;
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	public void launchFrame() { //登陆界面
		this.setLocation(400,300); //设置窗口位置
		this.setSize(300,300); //设置窗口大小
		
		/*添加tfTxt组件，并且按照BorderLayout布局将组件设置到南边（下边）
		 * 添加taContent组件，并且按照BorderLayout布局将组件设置到北边（上边）
		 * */
		add(tfTxt,BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		
		/*关闭窗口*/
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
		});
		
		/*按照自己设置布置窗口*/
		pack();
		tfTxt.addActionListener(new TFListener()); //添加自定义的监听器
		setVisible(true); //设置可见
		connect(); //连接
		new Thread(new RecvThread()).start(); //启动线程，接收信息
	}
	
	public void connect() { //连接
		try {
			s = new Socket("192.168.1.109",9100);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			bConnected = true;
			
			System.out.println("Client connected!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() { //断开连接
		try {
			dos.close();
			dis.close();
			s.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private class TFListener implements ActionListener{ 

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String str = tfTxt.getText().trim(); //当按下回车后，获取文本框中的消息，trim()用于消除文本前面和后尾的空格
			tfTxt.setText(""); //(发送消息后)把文本框置空
			
			try {
				dos.writeUTF(str); //往输出流写数据(用于发消息给服务器)
				dos.flush(); 	//强制把流缓冲区里的消息发出去，否则系统回归等到缓冲区满了之后才会发出去
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/*自定义用户接收来自服务器的消息的子线程*/
	private class RecvThread implements Runnable{

		@Override
		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF(); //从输入流里读取从服务端得到的消息
					taContent.setText(taContent.getText()+str+'\n');//把来自服务端的信息加上消息框一前的信息一起在消息框中打印出来
				}
			}catch(Exception e) {
				System.out.println("client close!"); //关闭客户端会造成异常，打印Client close提醒关闭程序
			}
		}
		
	}
}
