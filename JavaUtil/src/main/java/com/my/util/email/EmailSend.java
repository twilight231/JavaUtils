package com.my.util.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSend {
	public EmailSend() {
	}

	public static void main(String[] args) {
		String host = "smtp.163.com";// 你自己的主机
		String to = "mchotdog_011@126.com";// 要发给谁
		String from = "mchotdog_011@163.com";// 你的帐号
		String subject = "发送测试";// 主题
		String messageText = "http://ltchina.net";// 正文
		boolean sessionDebug = false;
		Properties props = new Properties();
		props.put("email.smtp.host", host);
		//props.put("email.transport.protocol", "smtp");
		props.put("email.smtp.auth", "true");
		// 系统需要的信息
		Session session = Session.getDefaultInstance(props, null);
		// 一次对话，一个session ，这个session 要货去固定的发送邮件信息
		session.setDebug(sessionDebug);
		try {
			MimeMessage meg = new MimeMessage(session);// 生成消息实例
			meg.setFrom(new InternetAddress(from));// 指定发件人
			InternetAddress[] address = { new InternetAddress(to) };// 生成收件人地址数组
			meg.setRecipients(Message.RecipientType.TO, address);// 指定收件人数组
			meg.setSubject(subject);// 指定主题
			meg.setText(messageText);// 指定正文
			meg.setSentDate(new Date());// 指定发送时间
			meg.saveChanges();// 保存信息
			Transport transport = session.getTransport("smtp");// 产生传输对象
			transport.connect("smtp.163.com", "mchotdog_011", "mchotdog");
			// 连接到你自己的主机
			transport.sendMessage(meg, meg.getAllRecipients());// 开始发送
			System.out.println("send   over");
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}