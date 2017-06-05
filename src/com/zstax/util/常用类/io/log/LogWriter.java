package com.zstax.util.常用类.io.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import book.io.ReadFromFile;

/**
 * ��־������
 * ʹ���˵���ģʽ����ֻ֤��һ��ʵ����
 * Ϊ�˸������������־�ļ�����ʹ�������ļ����á�
 * Ҳ�����ڳ�����ָ����־�ļ�����
 */
public class LogWriter {
	//  ��־�������ļ�
	public static final String LOG_CONFIGFILE_NAME = "log.properties";
	//  ��־�ļ����������ļ��еı�ǩ
	public static final String LOGFILE_TAG_NAME = "logfile";
	
	//	Ĭ�ϵ���־�ļ���·�����ļ�����
	private final String DEFAULT_LOG_FILE_NAME = "./logtext.log";
	//	�����Ψһ��ʵ��
	private static LogWriter logWriter;
	//  �ļ������
	private PrintWriter writer;  
	//  ��־�ļ���
	private String logFileName; 
	/**
	 * Ĭ�Ϲ��캯��
	 */
	private LogWriter() throws LogException{
		this.init();
	}
	private LogWriter(String fileName) throws LogException{
		this.logFileName = fileName;
		this.init();
	}
	/**
	 * ��ȡLogWriter��Ψһʵ����
	 * @return
	 * @throws LogException
	 */
	public synchronized static LogWriter getLogWriter()throws LogException{
		if (logWriter == null){
			logWriter = new LogWriter();
		}
		return logWriter;
	}
	public synchronized static LogWriter getLogWriter(String logFileName)throws LogException{
		if (logWriter == null){
			logWriter = new LogWriter(logFileName);
		}
		return logWriter;
	}

	/**
	 * ����־�ļ���дһ����־��Ϣ
	 * Ϊ�˷�ֹ���߳�ͬʱ����(д)��־�ļ�������ļ�����������ʹ��synchronized�ؼ���
	 * @param logMsg	��־��Ϣ
	 */
	public synchronized void log(String logMsg) {
		this.writer.println(new java.util.Date() + ": " + logMsg);
	}
	/**
	 * ����־�ļ���дһ���쳣��Ϣ
	 * ʹ��synchronized�ؼ��֡�
	 * @param ex	��д����쳣
	 */
	public synchronized void log(Exception ex) {
		writer.println(new java.util.Date() + ": ");
		ex.printStackTrace(writer);
	}

	/**
	 * ��ʼ��LogWriter
	 * @throws LogException
	 */
	private void init() throws LogException{
		//����û�û���ڲ�����ָ����־�ļ�������������ļ��л�ȡ��
		if (this.logFileName == null){
			this.logFileName = this.getLogFileNameFromConfigFile();
			//��������ļ������ڻ���Ҳû��ָ����־�ļ���������Ĭ�ϵ���־�ļ�����
			if (this.logFileName == null){
				this.logFileName = DEFAULT_LOG_FILE_NAME;
			}
		}
		File logFile = new File(this.logFileName);
		try {
			//	���е�FileWriter()�еĵڶ��������ĺ�����:�Ƿ����ļ���׷������
			//  PrintWriter()�еĵڶ��������ĺ����ǣ��Զ�������flush���ļ���
			writer = new PrintWriter(new FileWriter(logFile, true), true);
			System.out.println("��־�ļ���λ�ã�" + logFile.getAbsolutePath());
		} catch (IOException ex) {
			String errmsg = "�޷�����־�ļ�:" + logFile.getAbsolutePath();
			//  System.out.println(errmsg);
			throw new LogException(errmsg, ex);
		}
	}
	/**
	 * �������ļ���ȡ��־�ļ���
	 * @return
	 */
	private String getLogFileNameFromConfigFile() { 
		try { 
			Properties pro = new Properties(); 
			//����ĵ�ǰλ��,�������������ļ�log.properties 
			InputStream fin = getClass().getResourceAsStream(LOG_CONFIGFILE_NAME); 
			if (fin != null){
				pro.load(fin);//���������ļ�
				fin.close(); 
				return pro.getProperty(LOGFILE_TAG_NAME);
			} else {
				System.err.println("�޷������������ļ�: log.properties" ); 
			}
		}catch (IOException ex) { 
			System.err.println("�޷������������ļ�: log.properties" ); 
		}
		return null;
	}
	//�ر�LogWriter
	public void close() {
		logWriter = null;
		if (writer != null){
			writer.close();
		}
	}

	public static void main(String[] args) {
		LogWriter logger = null;
		try {
			String fileName = "C:/temp/temp0/logger.log";
			logger = LogWriter.getLogWriter(fileName);
			logger.log("First log!");
			logger.log("�ڶ�����־��Ϣ");
			logger.log("Third log");
			logger.log("���ĸ���־��Ϣ");
			logger.close();
			ReadFromFile.readFileByLines(fileName);
		} catch (LogException e) {
			e.printStackTrace();
		}
	}
}