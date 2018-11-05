package com.example.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CodeReturn extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/jar"); //希望浏览器看的出来这是一个JAR，而不是HTML。所以把内容类型设置为"application/jar"
		
		ServletContext ctx  = this.getServletContext();
		InputStream is = ctx.getResourceAsStream("/downloadFile/bookCode.jar"); //取得我们要的资源的输入流
		
		byte[] b = new byte[is.available()]; //创建字节数组
		is.read(b);  //将流读入到字节数组里面
		OutputStream os = resp.getOutputStream(); //获取响应的输出流
		os.write(b); //将字节数组写出去给客户
		os.flush();
		os.close();
		is.close();
	}
}
