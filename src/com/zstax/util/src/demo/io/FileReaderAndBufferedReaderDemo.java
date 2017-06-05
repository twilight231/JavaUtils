package com.zstax.util.src.demo.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Read��Write�ֱ��ӦInputStream��OutputStream
 * ǰ�������ַ��������������ֽ���
 * FileReader��FileWrite�ֱ��Ӧ��FileInputStream��FileOutputStream
 * BufferedReader��BufferedWrite�ֱ��Ӧ��BufferedInputStream��
 * BufferedOutputStream
 * ��ʾ��ʵ���ı��ļ����ַ���д
 */
public class FileReaderAndBufferedReaderDemo {
	public static String read(String fileName) throws IOException {
		StringBuilder str = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		while ((s = in.readLine()) != null)
			str.append(s + '\n');
		in.close();
		return str.toString();
	}

	public static void write(String fileName, boolean append)
			throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(fileName, append));
		out.write("����dahai!java hello!");
		out.close();
	}

	public static void main(String[] args) {
		try {
			write("file/test3.txt", false);
			System.out.println(read("file/test3.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
