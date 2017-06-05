package com.zstax.util.常用类.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ������׷�ӵ��ļ�β��
 */
public class AppendToFile {

	/**
	 * A����׷���ļ���ʹ��RandomAccessFile
	 * @param fileName	�ļ���
	 * @param content	׷�ӵ�����
	 */
	public static void appendMethodA(String fileName, String content){
		try {
			//	��һ����������ļ���������д��ʽ
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			//	�ļ����ȣ��ֽ���
			long fileLength = randomFile.length();
			//��д�ļ�ָ���Ƶ��ļ�β��
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * B����׷���ļ���ʹ��FileWriter
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodB(String fileName, String content){
		try {
			//��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String fileName = "C:/temp/newTemp.txt";
		String content = "new append!";
		//������A׷���ļ�
		AppendToFile.appendMethodA(fileName, content);
		AppendToFile.appendMethodA(fileName, "append end. \n");
		//��ʾ�ļ�����
		ReadFromFile.readFileByLines(fileName);
		//������B׷���ļ�
		AppendToFile.appendMethodB(fileName, content);
		AppendToFile.appendMethodB(fileName, "append end. \n");
		//��ʾ�ļ�����
		ReadFromFile.readFileByLines(fileName);
	}
}
