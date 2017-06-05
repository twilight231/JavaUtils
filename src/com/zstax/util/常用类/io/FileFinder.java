package com.zstax.util.常用类.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import book.arrayset.Queue;
/**
 * ʵ��һ��֧��ͨ����Ļ��ڹ�������㷨���ļ�������
 */
public class FileFinder {

	/**
	 * �����ļ���
	 * @param baseDirName		�����ҵ�Ŀ¼
	 * @param targetFileName	Ŀ���ļ�����֧��ͨ�����ʽ
	 * @param count				���������Ŀ�����η0�����ʾ����ȫ����
	 * @return		�����ѯ�������ļ����б�
	 */
	public static List findFiles(String baseDirName, String targetFileName, int count) {
		/**
		 * �㷨������
		 * ��ĳ������������ҵ��ļ��г������������ļ��е��������ļ��м��ļ���
		 * ��Ϊ�ļ��������ƥ�䣬ƥ��ɹ��������������Ϊ���ļ��У�������С�
		 * ���в��գ��ظ���������������Ϊ�գ�������������ؽ����
		 */
		List fileList = new ArrayList();
		//�ж�Ŀ¼�Ƿ����
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()){
			System.out.println("�ļ�����ʧ�ܣ�" + baseDirName + "����һ��Ŀ¼��");
			return fileList;
		}
		String tempName = null;
		//����һ�����У�Queue�ڵ������ж���
		Queue queue = new Queue();//ʵ�������� 
		queue.add(baseDir);//��� 
		File tempFile = null;
		while (!queue.isEmpty()) {
			//�Ӷ�����ȡĿ¼
			tempFile = (File) queue.pop();
			if (tempFile.exists() && tempFile.isDirectory()) {
				File[] files = tempFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					//�����Ŀ¼��Ž�����
					if (files[i].isDirectory()) { 
						queue.add(files[i]);
					} else {
						//������ļ�������ļ�����Ŀ���ļ�������ƥ�� 
						tempName =  files[i].getName(); 
						if (FileFinder.wildcardMatch(targetFileName, tempName)) {
							//ƥ��ɹ������ļ�����ӵ������
							fileList.add(files[i].getAbsoluteFile()); 
							//����Ѿ��ﵽָ������Ŀ�����˳�ѭ��
							if ((count != 0) && (fileList.size() >= count)) {
								return fileList;
							}
						}
					}
				}
			} 
		}
		
		return fileList;
	}
	/**
	 * ͨ���ƥ��
	 * @param pattern	ͨ���ģʽ
	 * @param str	��ƥ����ַ���
	 * @return	ƥ��ɹ��򷵻�true�����򷵻�false
	 */
	private static boolean wildcardMatch(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				//ͨ����Ǻ�*��ʾ����ƥ���������ַ�
				while (strIndex < strLength) {
					if (wildcardMatch(pattern.substring(patternIndex + 1),
							str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else if (ch == '?') {
				//ͨ����ʺ�?��ʾƥ������һ���ַ�
				strIndex++;
				if (strIndex > strLength) {
					//��ʾstr���Ѿ�û���ַ�ƥ��?�ˡ�
					return false;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}

	public static void main(String[] paramert) {
		//	�ڴ�Ŀ¼�����ļ�
		String baseDIR = "C:/temp"; 
		//	����չ��Ϊtxt���ļ�
		String fileName = "*.txt"; 
		//	��෵��5���ļ�
		int countNumber = 5; 
		List resultList = FileFinder.findFiles(baseDIR, fileName, countNumber); 
		if (resultList.size() == 0) {
			System.out.println("No File Fount.");
		} else {
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(resultList.get(i));//��ʾ���ҽ���� 
			}
		}
	}
}
