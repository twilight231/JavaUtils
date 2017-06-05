package com.zstax.util.常用类.io;

import java.io.File;
import java.io.IOException;

/**
 * �������ļ���Ŀ¼
 */
public class CreateFileUtil {

	/**
	 * ���������ļ�
	 * @param destFileName    Ŀ���ļ���
	 * @return �����ɹ�������true�����򷵻�false
	 */
	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ��Ѵ��ڣ�");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ�����ΪĿ¼��");
			return false;
		}
		// �ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
		if (!file.getParentFile().exists()) {
			// ���Ŀ���ļ����ڵ��ļ��в����ڣ��򴴽����ļ���
			System.out.println("Ŀ���ļ�����Ŀ¼�����ڣ�׼����������");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("����Ŀ���ļ����ڵ�Ŀ¼ʧ�ܣ�");
				return false;
			}
		}
		// ����Ŀ���ļ�
		try {
			if (file.createNewFile()) {
				System.out.println("���������ļ�" + destFileName + "�ɹ���");
				return true;
			} else {
				System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("���������ļ�" + destFileName + "ʧ�ܣ�" + e.getMessage());
			return false;
		}
	}

	/**
	 * ����Ŀ¼
	 * @param destDirName   Ŀ��Ŀ¼��
	 * @return Ŀ¼�����ɹ��Ż�true�����򷵻�false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѵ��ڣ�");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// ����Ŀ��Ŀ¼
		if (dir.mkdirs()) {
			System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
			return true;
		} else {
			System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�");
			return false;
		}
	}

	/**
	 * ������ʱ�ļ�
	 * @param prefix    ��ʱ�ļ�����ǰ׺
	 * @param suffix    ��ʱ�ļ����ĺ�׺
	 * @param dirName   ��ʱ�ļ����ڵ�Ŀ¼���������null�������û����ĵ�Ŀ¼�´�����ʱ�ļ�
	 * @return ��ʱ�ļ������ɹ�����true�����򷵻�false
	 */
	public static String createTempFile(String prefix, String suffix,
			String dirName) {
		File tempFile = null;
		if (dirName == null) {
			try {
				// ��Ĭ���ļ����´�����ʱ�ļ�
				tempFile = File.createTempFile(prefix, suffix);
				// ������ʱ�ļ���·��
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("������ʱ�ļ�ʧ��!" + e.getMessage());
				return null;
			}
		} else {
			File dir = new File(dirName);
			// �����ʱ�ļ�����Ŀ¼�����ڣ����ȴ���
			if (!dir.exists()) {
				if (CreateFileUtil.createDir(dirName)) {
					System.out.println("������ʱ�ļ�ʧ�ܣ����ܴ�����ʱ�ļ����ڵ�Ŀ¼��");
					return null;
				}
			}
			try {
				// ��ָ��Ŀ¼�´�����ʱ�ļ�
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("������ʱ�ļ�ʧ��!" + e.getMessage());
				return null;
			}
		}
	}

	public static void main(String[] args) {
		// ����Ŀ¼
		String dirName = "C:/temp/temp0/temp1";
		CreateFileUtil.createDir(dirName);
		// �����ļ�
		String fileName = dirName + "/temp2/tempFile.txt";
		CreateFileUtil.createFile(fileName);
		// ������ʱ�ļ�
		String prefix = "temp";
		String surfix = ".txt";
		for (int i = 0; i < 10; i++) {
			System.out.println("��������ʱ�ļ�: "
					+ CreateFileUtil.createTempFile(prefix, surfix, dirName));
		}
	}
}
