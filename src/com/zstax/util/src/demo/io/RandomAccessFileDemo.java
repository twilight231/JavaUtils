package com.zstax.util.src.demo.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * RandomAccessFileֱ�Ӽ̳�Object�����Խ����������������������c���Ե��ļ�����
 * Ҫָ����ʲô��ʽ���ļ����������ʱҪ֪���ļ����Ű棬�����ж�д�������ͺ�UTF-8�ַ���
 * �ĸ��ַ��������Զ�λ���ļ���ĳһλ�ý��ж�д
 */
public class RandomAccessFileDemo {
	public static void main(String[] args) throws FileNotFoundException {
		RandomAccessFile out = new RandomAccessFile("file/test5", "rw");
		try {
			out.writeInt(1);
			out.writeDouble(3.3);
			out.writeChar('a');
			out.writeUTF("hello,java!");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RandomAccessFile in = new RandomAccessFile("file/test5", "r");
		try {
			in.seek(4);
			System.out.println(in.readDouble());
			in.seek(4+8+2);
			System.out.println(in.readUTF());
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
