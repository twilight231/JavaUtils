package com.zstax.util.常用类.io;
import java.io.File;
import java.util.Date;

/**
 * ��ȡ�ļ��Ļ�����Ϣ
 */
public class GetFileInfos {

	public static void println(String s){
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		//���ļ�·���½�һ���ļ�����·�������Ǿ���·��Ҳ���������·��
		//����Ĳ���������Ϊ�ļ��ĳ���·��
		File file = new File("C:/temp/newTemp.txt");
		//��ȡ�ļ������֣�������·��
		println("�ļ���:\t" + file.getName());
		//������·�����е��ļ��ָ�����ϵͳĬ�Ϸָ����滻
		println("�ļ�·��:\t" + file.getPath());
		//��ȡ�ļ��ľ���·��
		println("����·��:\t" + file.getAbsolutePath());
		//��ȡ����·�����ĸ�����·��
		println("��Ŀ¼:\t" + file.getParent());
		println("�ļ��Ƿ����:\t" + file.exists());
		println("�Ƿ�ɶ�:\t" + file.canRead());
		println("�Ƿ��д:\t" + file.canWrite());
		println("�Ƿ��������ļ�:\t" + file.isHidden());
		println("�Ƿ�����ͨ�ļ�:\t" + file.isFile());
		println("�Ƿ����ļ�Ŀ¼:\t" + file.isDirectory());
		println("�ļ�·���Ƿ��Ǿ���·��:\t" + file.isAbsolute());
		println("�ļ�·����URI:\t" + file.toURI());
		println("�ļ�����޸�ʱ��:\t" + new Date(file.lastModified()));
		println("�ļ���С:\t" + file.length() + " bytes");
	}
}
