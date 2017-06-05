package com.zstax.util.src.demo.io;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
 * �̳���FilterOutputStream������DataOutputStream�������ݵĴ洢��PrintStream������ʾ
 * ���ڸ�ʽ����ӡ
 */
public class PrintStreamDemo {
	public static void main(String[] args) throws FileNotFoundException {
		// �����ݿ��ӻ���ʽ��ʾ���ı��ļ���
		PrintStream printStream = new PrintStream("file/test2.txt");
		printStream.println('a');
		printStream.println(2);
		printStream.println(3.2);
		printStream.println("liuhaifang");
		printStream.println("������");
		// ���ӻ���ʾ������̨
		printStream = new PrintStream(System.out);
		printStream.println("hello  java");
	}
}
