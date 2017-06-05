package com.zstax.util.常用类.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFileUtil {

	/**
	 * ���Ƶ����ļ��� ���Ŀ���ļ����ڣ��򲻸��ǡ�
	 * @param srcFileName	�����Ƶ��ļ���
	 * @param destFileName	Ŀ���ļ���
	 * @return		������Ƴɹ����򷵻�true�����򷵻�false
	 */
	public static boolean copyFile(String srcFileName, String destFileName){
		return CopyFileUtil.copyFile(srcFileName, destFileName, false);
	}
	
	/**
	 * ���Ƶ����ļ�
	 * @param srcFileName	�����Ƶ��ļ���	
	 * @param destFileName	Ŀ���ļ���
	 * @param overlay		���Ŀ���ļ����ڣ��Ƿ񸲸�
	 * @return	������Ƴɹ����򷵻�true�����򷵻�false
	 */
	public static boolean copyFile(String srcFileName, 
			String destFileName, boolean overlay) {
		//�ж�ԭ�ļ��Ƿ����
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()){
			System.out.println("�����ļ�ʧ�ܣ�ԭ�ļ�" + srcFileName + "�����ڣ�");
			return false;
		} else if (!srcFile.isFile()){
			System.out.println("�����ļ�ʧ�ܣ�" + srcFileName + "����һ���ļ���");
			return false;
		}
		//�ж�Ŀ���ļ��Ƿ����
		File destFile = new File(destFileName);
		if (destFile.exists()){
			//���Ŀ���ļ����ڣ����Ҹ���ʱ�����ǡ�
			if (overlay){
				//ɾ���Ѵ��ڵ�Ŀ���ļ�������Ŀ���ļ���Ŀ¼���ǵ����ļ�
				System.out.println("Ŀ���ļ��Ѵ��ڣ�׼��ɾ������");
				if(!DeleteFileUtil.delete(destFileName)){
					System.out.println("�����ļ�ʧ�ܣ�ɾ��Ŀ���ļ�" + destFileName + "ʧ�ܣ�");
					return false;
				}
			} else {
				System.out.println("�����ļ�ʧ�ܣ�Ŀ���ļ�" + destFileName + "�Ѵ��ڣ�");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()){
				//���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽�Ŀ¼
				System.out.println("Ŀ���ļ����ڵ�Ŀ¼�����ڣ�׼����������");
				if(!destFile.getParentFile().mkdirs()){
					System.out.println("�����ļ�ʧ�ܣ�����Ŀ���ļ����ڵ�Ŀ¼ʧ�ܣ�" );
					return false;
				}
			}
		}
		//׼�������ļ�
		int byteread = 0;//��ȡ��λ��
		InputStream in = null;
		OutputStream out = null;
		try {
			//��ԭ�ļ�
			in = new FileInputStream(srcFile);  
			//�����ӵ�Ŀ���ļ��������
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			//һ�ζ�ȡ1024���ֽڣ���bytereadΪ-1ʱ��ʾ�ļ��Ѿ�����
			while ((byteread = in.read(buffer)) != -1) {
				//����ȡ���ֽ�д�������
				out.write(buffer, 0, byteread);
			}
			System.out.println("���Ƶ����ļ�" + srcFileName + "��" + destFileName + "�ɹ���");
			return true;
		} catch (Exception e) {
			System.out.println("�����ļ�ʧ�ܣ�" + e.getMessage());
			return false;
		} finally {
			//�ر������������ע���ȹر���������ٹر�������
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ��������Ŀ¼�����ݣ����Ŀ��Ŀ¼���ڣ��򲻸���
	 * @param srcDirName	�����Ƶ�Ŀ¼��
	 * @param destDirName	Ŀ��Ŀ¼��
	 * @return		������Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName){
		return CopyFileUtil.copyDirectory(srcDirName, destDirName, false);
	}
	/**
	 * ��������Ŀ¼������
	 * @param srcDirName	�����Ƶ�Ŀ¼��
	 * @param destDirName	Ŀ��Ŀ¼��
	 * @param overlay		���Ŀ��Ŀ¼���ڣ��Ƿ񸲸�
	 * @return	������Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName,
			boolean overlay) {
		// �ж�ԭĿ¼�Ƿ����
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			System.out.println("����Ŀ¼ʧ�ܣ�ԭĿ¼" + srcDirName + "�����ڣ�");
			return false;
		} else if (!srcDir.isDirectory()) {
			System.out.println("����Ŀ¼ʧ�ܣ�" + srcDirName + "����һ��Ŀ¼��");
			return false;
		}
		// ���Ŀ���ļ����������ļ��ָ�����β���Զ�����ļ��ָ���
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// ���Ŀ���ļ��д��ڣ�
		if (destDir.exists()) {
			if (overlay) {
				// ��������ɾ���Ѵ��ڵ�Ŀ��Ŀ¼
				System.out.println("Ŀ��Ŀ¼�Ѵ��ڣ�׼��ɾ������");
				if (!DeleteFileUtil.delete(destDirName)) {
					System.out.println("����Ŀ¼ʧ�ܣ�ɾ��Ŀ��Ŀ¼" + destDirName + "ʧ�ܣ�");
				}
			} else {
				System.out.println("����Ŀ¼ʧ�ܣ�Ŀ��Ŀ¼" + destDirName + "�Ѵ��ڣ�");
				return false;
			}
		} else {
			// ����Ŀ��Ŀ¼
			System.out.println("Ŀ��Ŀ¼�����ڣ�׼����������");
			if (!destDir.mkdirs()) {
				System.out.println("����Ŀ¼ʧ�ܣ�����Ŀ��Ŀ¼ʧ�ܣ�");
				return false;
			}
		}
		boolean flag = true;
		// �г�Դ�ļ����������ļ���������Ŀ¼�����ļ���
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// �����һ�������ļ�������и���
			if (files[i].isFile()) {
				flag = CopyFileUtil.copyFile(files[i].getAbsolutePath(), 
						destDirName + files[i].getName());
				if (!flag){
					break;
				}
			}
			// �������Ŀ¼����������Ŀ¼
			if (files[i].isDirectory()) {
				flag = CopyFileUtil.copyDirectory(files[i].getAbsolutePath(), 
						destDirName + files[i].getName());
				if (!flag){
					break;
				}
			}
		}
		if (!flag){
			System.out.println("����Ŀ¼" + srcDirName + "��" + destDirName+ "ʧ�ܣ�");
			return false;
		}
		System.out.println("����Ŀ¼" + srcDirName + "��" + destDirName+ "�ɹ���");
		return true;

	}

	public static void main(String[] args) {
		//���Ƶ����ļ������Ŀ����ڣ��򸲸�
		String srcPath = "C:/temp/tempfile0.txt";
		String destPath = "C:/temp_bak/tempfile0_bak.txt";
		CopyFileUtil.copyFile(srcPath, destPath, true);
		//���Ŀ����ڣ��򲻸���
		CopyFileUtil.copyFile(srcPath, destPath);
		System.out.println();
		//�����ļ��У����Ŀ����ڣ��򸲸�
		String srcDir = "C:/temp";
		String destDir = "D:/temp";
		CopyFileUtil.copyDirectory(srcDir, destDir, true);
	}
}