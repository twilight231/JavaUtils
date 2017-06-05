package com.zstax.util.常用类.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * �ļ��ָ�ϲ����������ļ��ָ������С�ļ��������С�ļ��ϲ���һ�����ļ���
 */
public class FileDivisionUniter {
	//�ָ����ļ�����׺
	public static final String SUFFIX = ".pp";

	/**
	 * �ָ��ļ�
	 * @param fileName	���ָ���ļ���
	 * @param size	С�ļ��Ĵ�С����λ�ֽ�
	 * @return		�ָ�ɵ�С�ļ����ļ���
	 * @throws Exception
	 */
	public static String[] divide(String fileName, long size) throws Exception {

		File inFile = new File(fileName);
		if (!inFile.exists() || (!inFile.isFile())) {
			throw new Exception("ָ���ļ������ڣ�");
		}
		//��ñ��ָ��ļ����ļ����������ָ�ɵ�С�ļ���������Ŀ¼��
		File parentFile = inFile.getParentFile();
		
		//	ȡ���ļ��Ĵ�С
		long fileLength = inFile.length(); 
		if (size <=0){
			size = fileLength / 2;
		}
		// ȡ�ñ��ָ���С�ļ�����Ŀ
		int num = (fileLength % size != 0) ? (int)(fileLength / size + 1)
				: (int)(fileLength / size); 
		// ��ű��ָ���С�ļ���
		String[] outFileNames = new String[num];
		// �����ļ����������ָ���ļ�
		FileInputStream in = new FileInputStream(inFile);
		
		// �������ļ����Ŀ�ʼ�ͽ����±�
		long inEndIndex = 0;
		int inBeginIndex = 0;
		
		//����Ҫ�ָ����Ŀ����ļ�
		for (int outFileIndex = 0; outFileIndex < num; outFileIndex++) {
			//����ǰnum - 1��С�ļ�����С��Ϊָ����size
			File outFile = new File(parentFile, inFile.getName()
					+ outFileIndex + SUFFIX);
			// ����С�ļ��������
			FileOutputStream out = new FileOutputStream(outFile);
			//�������±����size
			inEndIndex += size;
			inEndIndex = (inEndIndex > fileLength) ? fileLength : inEndIndex;
			// ���������ж�ȡ�ֽڴ洢���������
			for (; inBeginIndex < inEndIndex; inBeginIndex++) {
				out.write(in.read());
			}
			out.close();
			outFileNames[outFileIndex] = outFile.getAbsolutePath();
		}
		in.close();
		return outFileNames;
	}

	/**
	 * �ϲ��ļ�
	 * @param fileNames		���ϲ����ļ�������һ������
	 * @param TargetFileName	Ŀ���ļ���
	 * @return		Ŀ���ļ���ȫ·��
	 * @throws Exception
	 */
	public static String unite(String[] fileNames, String TargetFileName)
			throws Exception {
		File inFile = null; 
		//�����ļ������
		File outFile = new File(TargetFileName);
		FileOutputStream out = new FileOutputStream(outFile);

		for (int i = 0; i < fileNames.length; i++) {
			// ���ļ�������
			inFile = new File(fileNames[i]);
			FileInputStream in = new FileInputStream(inFile);
			// ���������ж�ȡ���ݣ���д�뵽�ļ���������
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
		}
		out.close();
		
		return outFile.getAbsolutePath();
	}

	public static void main(final String[] args) throws Exception {
		//�ָ��ļ�
		String fileName = "C:/temp/temp.xls";
		long size = 1000;
		String[] fileNames = FileDivisionUniter.divide(fileName, size);
		System.out.println("�ָ��ļ�" + fileName + "�����");
		for (int i=0; i<fileNames.length; i++){
			System.out.println(fileNames[i]);	
		}
		//�ϲ��ļ�
		String newFileName = "C:/temp/newTemp.xls";
		System.out.println("�ϲ��ļ������" + 
				FileDivisionUniter.unite(fileNames, newFileName));
	}
}