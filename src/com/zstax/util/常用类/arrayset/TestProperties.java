package com.zstax.util.常用类.arrayset;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ���Լ����� Properties
 */
public class TestProperties {

	public static void main(String[] args) throws IOException {
		//�½�һ��Properties����
		Properties props = new Properties();
		
		//��Properties�д�����ݣ���ʽλ<key, value>
		//key �� value�����ַ���
		props.setProperty("name", "ZhangSan");
		props.setProperty("gender", "male");
		props.setProperty("age", "30");
		props.setProperty("telNO", "01088888888");
		props.setProperty("address", "xxxxxxxx");
		
		//��Properties�л�ȡ���ݡ������ṩkey
		System.out.println("name: " + props.getProperty("name"));
		System.out.println("gender: " + props.getProperty("gender"));
		System.out.println("age: " + props.getProperty("age"));
		System.out.println("telNO: " + props.getProperty("telNO"));
		System.out.println("address: " + props.getProperty("address"));
		//����λ����ֵ�ṩһ��ȱʡֵ����Properties��û�и�keyʱ����Ĭ��ֵ����
		System.out.println("other: " + props.getProperty("other", "none"));
		
		//��Properties�е����ݱ��浽������������ļ������
		String fileName = "c:/test.properties";
		FileOutputStream out = new FileOutputStream(fileName);
		props.store(out, "test");
		out.close();
		//��c:/test.properties�ļ������Կ�����������ݣ�ע�⵽��˳���Ǹı��˵ġ�
		
		Properties newProps = new Properties();
		newProps.setProperty("type", "newProps");
		//���Դ��������л�ȡ�������ݣ������ļ�������
		//��properties�ļ��м�������
		FileInputStream in = new FileInputStream(fileName);
		newProps.load(in);
		in.close();
		System.out.println();
		System.out.println("type: " + newProps.getProperty("type"));
		System.out.println("name: " + newProps.getProperty("name"));
		System.out.println("gender: " + newProps.getProperty("gender"));
		
		//��Properties�е����������һ�������
		System.out.println();
		props.list(System.out);
	}
}
