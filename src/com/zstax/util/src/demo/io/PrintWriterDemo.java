package com.zstax.util.src.demo.io;


import java.io.IOException;
import java.io.PrintWriter;

/*
 * ��Ӧ��PrintStream
 * ���ڸ�ʽ��������ļ�
 */
public class PrintWriterDemo {
	public static void main(String[] args) throws IOException {
		// �򻯵Ĵ�����ʽ
		PrintWriter out = new PrintWriter("file/test4.txt");
		// Ҳ�������������� out=new Printer(new BufferedWriter(new
		// FileWriter("file/test4.txt")));
		// ��ʽ��������ı�
		out.println('a');
		out.println(3);
		out.println(3.5);
		out.print("�Ұ��㣡i love you");
		out.close();
		// ���ı���ȡ�ղŵ�д��
		System.out.println(FileReaderAndBufferedReaderDemo
				.read("file/test4.txt"));
	}

}
