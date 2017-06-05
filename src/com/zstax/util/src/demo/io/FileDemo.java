package com.zstax.util.src.demo.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * File�����ļ���Ŀ¼,��̬���У���ϵͳ�йص�·���ָ�������ϵͳ�йص�Ĭ�����Ʒָ�����
 * ��Ҫ�����У������ļ���Ŀ¼��ɾ���ļ������ļ��趨���ԡ�����ָ��Ŀ¼�µ��ļ��б�
 *          ���ع��˺���ļ��б� ����ļ��Ƿ���ڡ��Ƿ����ء��Ƿ���Ŀ¼�����ļ���
 *          �����ļ����ƺ�·��
 * 
 * @author Touch
 *
 */
public class FileDemo {
	/*
	 * ����ָ��·���µ�ƥ��regex���ļ�
	 */
	public String[] find(String path, final String regex) {
		File file = new File(path);
        //�����ڲ���
		return file.list(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return pattern.matcher(name).matches();
			}
		});
	}

	public static void main(String[] args) {
		String path = null;
		String key = null;
		String regex = null;
		int choice = 1;
		Scanner scanner = new Scanner(System.in);
		System.out.println("please input the file path:");
		path = scanner.next();
		System.out.println("please input key:");
		key = scanner.next();
		System.out.println("choise:\n0:ƥ����" + key + "Ϊ��׺���ļ�\n1��ƥ�����" + key
				+ "���ļ�");
		if ((choice = scanner.nextInt()) == 0)
			regex = ".*\\." + key;
		else
			regex = ".*" + key + ".*";
		String[] list;
		list = new FileDemo().find(path, regex);
		System.out.println(Arrays.deepToString(list));
		//����ָ��·���µ�Ŀ¼�б�
		File[] fileList = new File(path).listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				list = new FileDemo().find(file.getPath(), regex);
				System.out.println(Arrays.deepToString(list));
			}
		}
	}

}
