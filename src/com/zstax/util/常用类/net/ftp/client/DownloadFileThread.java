package com.zstax.util.常用类.net.ftp.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import sun.net.ftp.FtpClient;

/**
 * �����ļ����߳�
 */
public class DownloadFileThread extends Thread {
	
	private boolean running = false;
	
	// FTP��������IP
	String ip = "";
	// ���ӷ��������û���������
	String username = "";
	String password = "";
	// �������ļ���FTP�ϵ�Ŀ¼���ļ���
	String ftpDir = "";
	String ftpFileName = "";
	// ���ص����غ���ļ���
	String localFileName = "";
	
	MainFrame frame = null;

	// ���췽��
	public DownloadFileThread(MainFrame frame, String server, String username,
			String password, String path, String filename, String userpath) {
		this.ip = server;
		this.username = username;
		this.password = password;
		this.ftpDir = path;
		this.ftpFileName = filename;
		this.localFileName = userpath;
		this.frame = frame;
	}

	public void run() {
		running = true;
		FileOutputStream os = null;
		InputStream is = null;
		FtpClient ftpClient = null;
		try {
			String savefilename = localFileName;
			// �½�FTP�ͻ���
			ftpClient = new FtpClient();
			ftpClient.openServer(ip);
			// ��½
			ftpClient.login(username, password);
			if (ftpDir.length() != 0){
				// �л���ָ����Ŀ¼��
				ftpClient.cd(ftpDir);
			}
			// �Զ����Ƹ�ʽ��FTP
			ftpClient.binary();
			// ���ļ�
			is = ftpClient.get(ftpFileName);
			// ���浽����
			File file_out = new File(savefilename);
			os = new FileOutputStream(file_out);
			byte[] bytes = new byte[1024];
			// ��ʼ����
			int c;
			frame.taskList.add(ftpFileName);
			frame.consoleTextArea.append("downloading the file " + ftpFileName
					+ " , wait for a moment!\n");
			while (running && ((c = is.read(bytes)) != -1)) {
				os.write(bytes, 0, c);
			}
			if (running){
				// ���سɹ������������߳�
				frame.taskList.remove(ftpFileName);
				frame.consoleTextArea.append(" the file " + ftpFileName
						+ " download has finished!\n");
				frame.performTaskThreads.removeElement(this);
			}

		} catch (Exception e) {
			// ����ʧ��
			frame.consoleTextArea.append(" the file " + ftpFileName
					+ " ,download has problem!\n");
			frame.performTaskThreads.removeElement(this);
		} finally {
			try {
				if (is != null){
					is.close();
				}
				if (os != null){
					os.close();
				}
				if (ftpClient != null){
					ftpClient.closeServer();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void toStop(){
		this.running = false;
	}

}