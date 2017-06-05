package com.zstax.util.src.demo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * DataInputStream�̳���FilterInputStream(FilterInputStream�̳���InputStream)
 * ����װ��InputStream���ṩ����ֲ��ʽ������ȡ������������
 * DataOutputStream�̳���FilterOutputStream(FilterOutputStream�̳���OutputStream)
 * ����װ��OutputStream���ṩ����ֲ��ʽ����д�������������
 * DataInputStream/DataOutputStream����ʵ�����ݵĴ洢��ָ�
 */
public class DataInputStreamDemo {
	public static void main(String[] args) {
		DataOutputStream dataOutStream = null;
		try {
			dataOutStream = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream("file/aa.data")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {// д���ļ�
			dataOutStream.writeChar('a');
			dataOutStream.writeInt(3);
			dataOutStream.writeDouble(5.5);
			dataOutStream.writeFloat(3.2f);
			dataOutStream.writeUTF("nihaoma");
			dataOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(new BufferedInputStream(
					new FileInputStream("file/aa.data")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {// ��ȡ�ļ�
			System.out.println(dataInputStream.readChar());
			System.out.println(dataInputStream.readInt());
			System.out.println(dataInputStream.readDouble());
			System.out.println(dataInputStream.readFloat());
			System.out.println(dataInputStream.readUTF());
			dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
