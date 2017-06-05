package com.zstax.util.常用类.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * �ʼ������֤�����ڷ����ʼ�ʱʹ��
 */
public class MyAuthenticator extends Authenticator{
	// ��½�����ʼ����������û���
	private String userName;
	// ��½�����ʼ�������������
	private String password;
	public MyAuthenticator(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	/**
	 * ���Ǹ���ĸ÷��������������֤��
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
