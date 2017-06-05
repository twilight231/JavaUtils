package com.zstax.util.常用类.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ��ZIPѹ���ͽ�ѹ���ļ���Ŀ¼
 */
public class CompressUtil {

	/**
	 * ѹ���ļ�����Ŀ¼
	 * @param baseDirName		ѹ���ĸ�Ŀ¼
	 * @param fileName			��Ŀ¼�´�ѹ�����ļ����ļ�������
	 * �Ǻ�*��ʾѹ����Ŀ¼�µ�ȫ���ļ���
	 * @param targetFileName	Ŀ��ZIP�ļ�
	 */
	public static void zipFile(String baseDirName, String fileName, 
			String targetFileName){
		//����Ŀ¼�Ƿ����
		if (baseDirName == null){
			System.out.println("ѹ��ʧ�ܣ���Ŀ¼�����ڣ�" + baseDirName);
			return;
		}
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || (!baseDir.isDirectory())){
			System.out.println("ѹ��ʧ�ܣ���Ŀ¼�����ڣ�" + baseDirName);
			return;
		}
		String baseDirPath = baseDir.getAbsolutePath();
		//Ŀ���ļ�
		File targetFile = new File(targetFileName);
		try{
			//����һ��zip�������ѹ�����ݲ�д�뵽zip�ļ� 
			ZipOutputStream out =new ZipOutputStream(
					new FileOutputStream(targetFile)); 
			if (fileName.equals("*")){
				//��baseDirĿ¼�µ������ļ�ѹ����ZIP
				CompressUtil.dirToZip(baseDirPath, baseDir, out);
			} else {
				File file = new File(baseDir, fileName);
				if (file.isFile()){
					CompressUtil.fileToZip(baseDirPath, file, out);
				} else {
					CompressUtil.dirToZip(baseDirPath, file, out);
				}
			}
			out.close(); 
			System.out.println("ѹ���ļ��ɹ���Ŀ���ļ�����" + targetFileName);
		} catch (IOException e){
			System.out.println("ѹ��ʧ�ܣ�" + e);
			e.printStackTrace();
		}
	}

	/**
	 * ��ѹ��ZIP�ļ�����ZIP�ļ�������ݽ�ѹ��targetDIRĿ¼��
	 * @param zipName	����ѹ����ZIP�ļ���
	 * @param targetBaseDirName	Ŀ��Ŀ¼
	 */
	public static void upzipFile(String zipFileName, String targetBaseDirName){
		if (!targetBaseDirName.endsWith(File.separator)){
			targetBaseDirName += File.separator;
		}
        try {
        	//����ZIP�ļ�����ZipFile����
        	ZipFile zipFile = new ZipFile(zipFileName);
            ZipEntry entry = null;
            String entryName = null;
            String targetFileName = null;
            byte[] buffer = new byte[4096];
            int bytes_read; 
            //��ȡZIP�ļ������е�entry
            Enumeration entrys = zipFile.entries();
            //��������entry
            while (entrys.hasMoreElements()) {
            	entry = (ZipEntry)entrys.nextElement();
            	//���entry������
            	entryName =  entry.getName();
            	targetFileName = targetBaseDirName + entryName;
            	if (entry.isDirectory()){
            		//  ���entry��һ��Ŀ¼���򴴽�Ŀ¼
            		new File(targetFileName).mkdirs();
            		continue;
            	} else {
            		//	���entry��һ���ļ����򴴽���Ŀ¼
            		new File(targetFileName).getParentFile().mkdirs();
            	}

            	//���򴴽��ļ�
            	File targetFile = new File(targetFileName);
            	System.out.println("�����ļ���" + targetFile.getAbsolutePath());
            	//���ļ������
            	FileOutputStream os = new FileOutputStream(targetFile);
            	//��ZipFile�����д�entry��������
            	InputStream  is = zipFile.getInputStream(entry);
            	while ((bytes_read = is.read(buffer)) != -1){
            		os.write(buffer, 0, bytes_read);
            	}
            	//�ر���
            	os.close( );
            	is.close( );
            }
            System.out.println("��ѹ���ļ��ɹ���");
        } catch (IOException err) {
            System.err.println("��ѹ���ļ�ʧ��: " + err);
        }
	}
	
	/**
	 * ��Ŀ¼ѹ����ZIP�������
	 */
	private static void dirToZip(String baseDirPath, File dir, 
			ZipOutputStream out){
		if (dir.isDirectory()){
			//�г�dirĿ¼�������ļ�
			File[] files  = dir.listFiles();
			// ����ǿ��ļ���
			if (files.length == 0){
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));  
				//	�洢Ŀ¼��Ϣ
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			for (int i=0; i<files.length; i++){
				if (files[i].isFile()){
					//������ļ�������fileToZip����
					CompressUtil.fileToZip(baseDirPath, files[i], out);
				} else {
					//�����Ŀ¼���ݹ����
					CompressUtil.dirToZip(baseDirPath, files[i], out);
				}
			}
		}
	}
	/**
	 * ���ļ�ѹ����ZIP�����
	 */
	private static void fileToZip(String baseDirPath, File file, 
			ZipOutputStream out){
		FileInputStream in = null;
		ZipEntry entry = null;
		//	�������ƻ�����
		byte[] buffer = new byte[4096];    
		int bytes_read; 
		if (file.isFile()){
			try {
				// ����һ���ļ�������	
				in = new FileInputStream(file);  
				//	��һ��ZipEntry
				entry = new ZipEntry(getEntryName(baseDirPath, file));  
				//	�洢����Ϣ��ѹ���ļ�
				out.putNextEntry(entry);
				//	�����ֽڵ�ѹ���ļ�
				while((bytes_read = in.read(buffer)) != -1){  
					out.write(buffer, 0, bytes_read); 
				}
				out.closeEntry();
				in.close(); 
				System.out.println("����ļ�" 
						+ file.getAbsolutePath() + "����ZIP�ļ��У�");
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * ��ȡ��ѹ���ļ���ZIP�ļ���entry�����֡�������ڸ�Ŀ¼�����·����
	 * @param baseDirPath
	 * @param file
	 * @return
	 */
	private static String getEntryName(String baseDirPath, File file){
		if (!baseDirPath.endsWith(File.separator)){
			baseDirPath += File.separator;
		}
		String filePath = file.getAbsolutePath();
		// ����Ŀ¼��������entry���ֺ������"/"����ʾ������Ŀ¼��洢��
		if (file.isDirectory()){
			filePath += "/";
		}
		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}
	
	public static void main(String[] args) {
		//ѹ��C���µ�tempĿ¼��ѹ������ļ���C:/temp.zip
		String baseDirName = "C:/";
		String fileName = "temp/";
		String zipFileName = "C:/temp.zip";
		CompressUtil.zipFile(baseDirName, fileName, zipFileName);
		//���մ�����ZIP�ļ���ѹ����D�̵�tempĿ¼��
		System.out.println();
		fileName = "D:/temp";
		CompressUtil.upzipFile(zipFileName, fileName);
	}
}
