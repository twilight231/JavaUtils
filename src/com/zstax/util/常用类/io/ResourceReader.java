package com.zstax.util.常用类.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import book.basic.HelloWorld;

/**
 *��jar���ж���Դ�ļ����������ļ���
 */
public class ResourceReader {

	/**
	 * ��һ�ַ�����Jar���е���Դ��Ϣ�����ȶ�ȡ��Դ��URL���ٶ�ȡURL��Ӧ���ļ���Ϣ
	 * @param class1	��
	 * @param fileName	�ļ������·��
	 */
	public static void readFromJarA(Class class1, String fileName) {
		//getResource�õ�һ��URL��������λ��Դ
		URL fileURL = class1.getResource(fileName);
		System.out.println("��Դ��URL: " + fileURL);
		try {
			//��fileURL��Ӧ���ļ���
			InputStream inputstream = fileURL.openStream();
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream));
			String str;
			
			while ((str = bufferedreader.readLine()) != null) {
				System.out.println(str);
			}
			inputstream.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}
	/**
	 * �ڶ��ַ�����Jar���е���Դ��Ϣ��
	 * @param class1	��
	 * @param fileName	�ļ������·��
	 */
	public static void readFromJarB(Class class1, String fileName) {
		//getResourceAsStreamȡ�ø���Դ�����������ã���֤������Դ���ȷ��λ�ó�ȡ����
		InputStream inputstream = class1.getResourceAsStream(fileName);
		if (inputstream != null) {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(inputstream));
			String str;
			try {
				while ((str = bufferedreader.readLine()) != null) {
					System.out.println(str);
				}
				inputstream.close();
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Class class1 = HelloWorld.class;
		//����ļ����·��ǰû��"/"�����ʾ����ڸ�class�ļ���λ��
		String filePath = "config0.conf";
		ResourceReader.readFromJarA(class1, filePath);
		System.out.println();
		filePath = "Resources/config1.conf";
		ResourceReader.readFromJarB(class1, filePath);
		System.out.println();
		
		//������ļ����·��ǰ���� "/"�����ʾ�����jar�ļ��ĸ�Ŀ¼λ�á�
		//��"/Resources"���ʾjar�ļ��ĵ�һ��Ŀ¼��Ŀ¼ΪResources
		filePath = "/book/basic/Resources/config2.conf";
		ResourceReader.readFromJarA(class1, filePath);
	}
}
