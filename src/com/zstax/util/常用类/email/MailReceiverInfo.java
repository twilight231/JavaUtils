package com.zstax.util.常用类.email;

import java.io.File;
import java.util.Properties;
/**
 * ���ʼ��Ļ�����Ϣ
 */
public class MailReceiverInfo {
	// �ʼ���������IP���˿ں�Э��
	private String mailServerHost;
	private String mailServerPort = "110";
	private String protocal = "pop3";
	// ��½�ʼ����������û���������
	private String userName;
	private String password;
	// �����ʼ���·��
	private String attachmentDir = "C:/temp/";
	private String emailDir = "C:/temp/";
	private String emailFileSuffix = ".eml";
	// �Ƿ���Ҫ�����֤
	private boolean validate = true;

	/**
	 * ����ʼ��Ự����
	 */
	public Properties getProperties(){
		Properties p = new Properties();
		p.put("mail.pop3.host", this.mailServerHost);
		p.put("mail.pop3.port", this.mailServerPort);
		p.put("mail.pop3.auth", validate ? "true" : "false");
		return p;
	}
	
	public String getProtocal() {
		return protocal;
	}

	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	public String getAttachmentDir() {
		return attachmentDir;
	}

	public void setAttachmentDir(String attachmentDir) {
		if (!attachmentDir.endsWith(File.separator)){
			attachmentDir = attachmentDir + File.separator;
		}
		this.attachmentDir = attachmentDir;
	}

	public String getEmailDir() {
		return emailDir;
	}

	public void setEmailDir(String emailDir) {
		if (!emailDir.endsWith(File.separator)){
			emailDir = emailDir + File.separator;
		}
		this.emailDir = emailDir;
	}

	public String getEmailFileSuffix() {
		return emailFileSuffix;
	}

	public void setEmailFileSuffix(String emailFileSuffix) {
		if (!emailFileSuffix.startsWith(".")){
			emailFileSuffix = "." + emailFileSuffix;
		}
		this.emailFileSuffix = emailFileSuffix;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
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

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}
