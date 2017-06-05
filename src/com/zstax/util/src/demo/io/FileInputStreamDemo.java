package com.zstax.util.src.demo.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * FileInputStream���ļ��в�����������FileOutputStream
 * �������������ļ�����/д���򿪺͹رն��ǵ��ñ��ط���
 */
public class FileInputStreamDemo {
	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("file/bb.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// ����һ���ֽ�����
		byte[] result = new byte[500];
		// int b;
		// while ((b = inputStream.read()) != -1)//��һ���ֽ�
		// System.out.print((char) b);
		inputStream.read(result);
		// System.out.println(Arrays.toString(result));
		inputStream.close();
		FileOutputStream outputStream = null;
		// true��ʾ��׷�ӵ���ʽ��
		outputStream = new FileOutputStream("file/bb.dat", true);
		// д��
		outputStream.write((int) 'A');
		outputStream.write(result);
		outputStream.close();
	}
}
