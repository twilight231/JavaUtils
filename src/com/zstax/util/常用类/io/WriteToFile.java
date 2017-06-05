package com.zstax.util.常用类.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
/**
 * ���ַ�ʽд�ļ�
 */
public class WriteToFile {
	/**
	 * ���ֽ�Ϊ��λд�ļ����ʺ���д�������ļ�����ͼƬ��
	 * @param fileName	�ļ���
	 */
	public static void writeFileByBytes(String fileName){
		File file = new File(fileName);
		OutputStream out= null;
		try {
			// ���ļ������
			out = new FileOutputStream(file);
			String content = "�ļ����ݣ�\n1,The First line;\n2,The second line.";
			byte[] bytes = content.getBytes();
			//д���ļ�
			out.write(bytes);
			System.out.println("д�ļ�" + file.getAbsolutePath() + "�ɹ���");
		} catch (IOException e){
			System.out.println("д�ļ�" + file.getAbsolutePath() + "ʧ�ܣ�");
			e.printStackTrace();
		} finally {
			if (out != null){
				try {
					//�ر�����ļ���
					out.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * ���ַ�Ϊ��λд�ļ���
	 * @param fileName	�ļ���
	 */
	public static void writeFileByChars(String fileName){
		File file = new File(fileName);
		Writer writer = null;
		try {
			//���ļ������
			writer = new OutputStreamWriter(new FileOutputStream(file));
			String content = "�ļ����ݣ�\n1,The First line;\n2,The second line.";
			writer.write(content);
			System.out.println("д�ļ�" + file.getAbsolutePath() + "�ɹ���");
		} catch (IOException e){
			System.out.println("д�ļ�" + file.getAbsolutePath() + "ʧ�ܣ�");
			e.printStackTrace();
		} finally {
			if (writer != null){
				try {
					//�ر�����ļ���
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	/**
	 * ����Ϊ��λд�ļ�
	 * @param fileName	�ļ���
	 */
	public static void writeFileByLines(String fileName){
		File file = new File(fileName);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			//д�ַ���
			writer.println("�ļ����ݣ�");
			//��д���ֻ�����������
			writer.print(true);
			writer.print(155);
			//����
			writer.println();
			//д���ļ�
			writer.flush();
			System.out.println("д�ļ�" + file.getAbsolutePath() + "�ɹ���");
		} catch (FileNotFoundException e) {
			System.out.println("д�ļ�" + file.getAbsolutePath() + "ʧ�ܣ�");
			e.printStackTrace();
		} finally {
			if (writer != null){
				//�ر�����ļ���
				writer.close();
			}
		}
	}
	
	public static void main(String[] args) {
		String fileName = "c:/temp/tempfile0.txt";
		WriteToFile.writeFileByBytes(fileName);
		fileName = "c:/temp/tempfile1.txt";
		WriteToFile.writeFileByChars(fileName);
		fileName = "c:/temp/tempfile2.txt";
		WriteToFile.writeFileByLines(fileName);
	}
}
