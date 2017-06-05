package com.zstax.util.常用类.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ���ݶ�������XML�ĵ�.
 * ʹ��Java�ṩ��java.beans.XMLEncoder��java.beans.XMLDecoder�ࡣ
 * ����JDK 1.4�Ժ�ų��ֵ���
 */
public class Object2XML {

	/**
	 * ���������XML�ļ�
	 * @param obj	������Ķ���
	 * @param outFileName	Ŀ��XML�ļ����ļ���
	 * @return	�������XML�ļ���·��
	 * @throws FileNotFoundException
	 */
	public static String object2XML(Object obj, String outFileName)
			throws FileNotFoundException {
		// �������XML�ļ����ֽ������
		File outFile = new File(outFileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(outFile));
		// ����һ��XML������
		XMLEncoder xmlEncoder = new XMLEncoder(bos);
		// ʹ��XML������д����
		xmlEncoder.writeObject(obj);
		// �رձ�����
		xmlEncoder.close();
		
		return outFile.getAbsolutePath();
	}

	/**
	 * ��XML�ļ�����ɶ���
	 * @param inFileName	�����XML�ļ�
	 * @return	�������ɵĶ���
	 * @throws FileNotFoundException
	 */
	public static Object xml2Object(String inFileName)
			throws FileNotFoundException {
		// ���������XML�ļ����ֽ�������
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(inFileName));
		// ����һ��XML������
		XMLDecoder xmlDecoder = new XMLDecoder(bis);
		// ʹ��XML������������
		Object obj = xmlDecoder.readObject();
		// �رս�����
		xmlDecoder.close();
		
		return obj;
	}

	public static void main(String[] args) throws IOException {

		// ����һ��StudentBean����
		StudentBean student = new StudentBean();
		student.setName("wamgwu");
		student.setGender("male");
		student.setAge(15);
		student.setPhone("55556666");
		
		// ��StudentBean����д��XML�ļ�
		String fileName = "AStudent.xml";
		Object2XML.object2XML(student, fileName);
		// ��XML�ļ���StudentBean����
		StudentBean aStudent = (StudentBean)Object2XML.xml2Object(fileName);
		// ��������Ķ���
		System.out.println(aStudent.toString());
	}
}