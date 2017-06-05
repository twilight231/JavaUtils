package com.zstax.util.常用类.net.ftp.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import sun.net.ftp.FtpClient;

/**
 * �ϴ��ļ���FTP������
 */
public class UploadFileThread extends Thread {

	private boolean running = false;
	// FTP������IP��ַ
	String ip = "";
	// ���ӷ��������û���������
	String username = "";
	String password = "";
	// �ļ��ϴ���FTP�������ϵ�Ŀ¼���ļ���
	String ftpDir = "";
	String ftpFileName = "";
	// ���ϴ����ļ����ļ�ȫ��
	String localFileFullName = "";
	
	MainFrame frame = null;

	// ���췽��
	public UploadFileThread(MainFrame frame, String serverIP, String username,
			String password, String ftpDir, String ftpFileName, String localFileName) {
		this.ip = serverIP;
		this.username = username;
		this.password = password;
		this.ftpDir = ftpDir;
		this.ftpFileName = ftpFileName;
		this.localFileFullName = localFileName;
		this.frame = frame;
	}

	public void run() {
		running = true;
		FtpClient ftpClient = null;
		OutputStream os = null;
		FileInputStream is = null;
		try {
			String savefilename = localFileFullName;
			// �½�һ��FTP�ͻ�������
			ftpClient = new FtpClient();
			ftpClient.openServer(ip);
			// ��½��FTP������
			ftpClient.login(username, password);
			if (ftpDir.length() != 0){
				// �л���Ŀ��Ŀ¼��
				ftpClient.cd(ftpDir);
			}
			// �Զ����ƴ�FTP
			ftpClient.binary();
			// ׼����FTP�������ϴ���ļ�
			os = ftpClient.put(ftpFileName);
			// �򿪱��ش��ϴ����ļ�
			File file_in = new File(savefilename);
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];

			// ��ʼ����
			int c;
			frame.taskList.add(ftpFileName);
			frame.consoleTextArea.append("uploading the file " + ftpFileName
					+ " , wait for a moment!\n");
			while (running && ((c = is.read(bytes)) != -1)) {
				os.write(bytes, 0, c);
			}
			if (running) {
				// ��ʱ�Ѿ��ϴ���ϣ������������ɾ�����ϴ�����
				frame.taskList.remove(ftpFileName);
				// ����̨��Ϣ������ļ��ϴ���ϵ���Ϣ
				frame.consoleTextArea.append(" the file " + ftpFileName
						+ " upload has finished!\n");
				// ���±������
				frame.setTableData();
				// ��������߳�
				frame.performTaskThreads.removeElement(this);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// �ϴ�ʧ��
			frame.consoleTextArea.append(" the file " + ftpFileName
					+ " ,upload has problem!\n");
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