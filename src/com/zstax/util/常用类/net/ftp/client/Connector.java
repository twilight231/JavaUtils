package com.zstax.util.常用类.net.ftp.client;

import sun.net.ftp.FtpClient;

/**
 * ����FTP������
 */
public class Connector extends Thread {

	MainFrame frame = null;
	// ��ҪIP��ַ���û���������
	String ip = "";
	String username = "";
	String password = "";

	public Connector(MainFrame frame, String ip, String username,
			String password) {
		// ��ʼ����Ϣ
		this.frame = frame;
		this.ip = ip;
		this.username = username;
		this.password = password;
	}

	public void run() {
		try {
			frame.consoleTextArea.append("connecting the server " + ip
					+ ",wait for a moment..\n");
			frame.ftpClient = new FtpClient();
			// ���ӷ�����
			frame.ftpClient.openServer(ip);
			frame.consoleTextArea.append("connect server " + ip
					+ " succeed,please continue!\n");
			// ���û����������½
			frame.ftpClient.login(username, password);
			frame.currentDirTextField.setText("/");
			frame.setTableData();
			frame.serverIPTextField.setText(ip);
			frame.userNameTextField.setText(username);
			frame.passwordTextField.setText(password);
		} catch (Exception e) {
			frame.consoleTextArea.append("can not connect the server " + ip + "!\n");
		}
	}

}