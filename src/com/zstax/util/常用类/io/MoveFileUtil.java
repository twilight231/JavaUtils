
package com.zstax.util.常用类.io;
import java.io.File;
/**
 * �ƶ��ļ���Ŀ¼
 */
public class MoveFileUtil {

	/**
	 * �ƶ������ļ����������Ѵ��ڵ�Ŀ���ļ�
	 * @param srcFileName	���ƶ���ԭ�ļ���	
	 * @param destFileName	Ŀ���ļ���
	 * @return		�ļ��ƶ��ɹ�����true�����򷵻�false
	 */
	public static boolean moveFile(String srcFileName, String destFileName){
		//Ĭ��Ϊ������Ŀ���ļ�
		return MoveFileUtil.moveFile(srcFileName, destFileName, false);
	}
	/**
	 * �ƶ������ļ�
	 * @param srcFileName	���ƶ���ԭ�ļ���
	 * @param destFileName	Ŀ���ļ���
	 * @param overlay		���Ŀ���ļ����ڣ��Ƿ񸲸�
	 * @return	�ļ��ƶ��ɹ�����true�����򷵻�false
	 */
	public static boolean moveFile(String srcFileName, 
			String destFileName, boolean overlay){
		//�ж�ԭ�ļ��Ƿ����
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()){
			System.out.println("�ƶ��ļ�ʧ�ܣ�ԭ�ļ�" + srcFileName + "�����ڣ�");
			return false;
		} else if (!srcFile.isFile()){
			System.out.println("�ƶ��ļ�ʧ�ܣ�" + srcFileName + "����һ���ļ���");
			return false;
		}
		File destFile = new File(destFileName);
		//���Ŀ���ļ�����
		if (destFile.exists()){
			//��������ļ�����
			if (overlay){
				//ɾ���Ѵ��ڵ�Ŀ���ļ�������Ŀ���ļ���Ŀ¼���ǵ����ļ�
				System.out.println("Ŀ���ļ��Ѵ��ڣ�׼��ɾ������");
				if(!DeleteFileUtil.delete(destFileName)){
					System.out.println("�ƶ��ļ�ʧ�ܣ�ɾ��Ŀ���ļ�" + destFileName + "ʧ�ܣ�");
					return false;
				}
			} else {
				System.out.println("�ƶ��ļ�ʧ�ܣ�Ŀ���ļ�" + destFileName + "�Ѵ��ڣ�");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()){
				//���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽�Ŀ¼
				System.out.println("Ŀ���ļ�����Ŀ¼�����ڣ�׼����������");
				if(!destFile.getParentFile().mkdirs()){
					System.out.println("�ƶ��ļ�ʧ�ܣ�����Ŀ���ļ����ڵ�Ŀ¼ʧ�ܣ�" );
					return false;
				}
			}
		}
		//�ƶ�ԭ�ļ���Ŀ���ļ�
		if (srcFile.renameTo(destFile)){
			System.out.println("�ƶ������ļ�" + srcFileName + "��" + destFileName + "�ɹ���");
			return true;
		} else {
			System.out.println("�ƶ������ļ�" + srcFileName + "��" + destFileName  + "ʧ�ܣ�");
			return true;
		}
	}
	
	/**
	 * �ƶ�Ŀ¼���������Ѵ��ڵ�Ŀ��Ŀ¼
	 * @param srcDirName	���ƶ���ԭĿ¼��
	 * @param destDirName	Ŀ��Ŀ¼��
	 * @return		Ŀ¼�ƶ��ɹ�����true�����򷵻�false
	 */
	public static boolean moveDirectory(String srcDirName, String destDirName){
		//Ĭ��Ϊ������Ŀ���ļ�
		return MoveFileUtil.moveDirectory(srcDirName, destDirName, false);
	}
	
	/**
	 * �ƶ�Ŀ¼��
	 * @param srcDirName	���ƶ���ԭĿ¼��
	 * @param destDirName	Ŀ��Ŀ¼��
	 * @param overlay		���Ŀ��Ŀ�۴��ڣ��Ƿ񸲸�
	 * @return		Ŀ¼�ƶ��ɹ�����true�����򷵻�false
	 */
	public static boolean moveDirectory(String srcDirName, 
			String destDirName, boolean overlay){
		//�ж�ԭĿ¼�Ƿ����
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()){
			System.out.println("�ƶ�Ŀ¼ʧ�ܣ�ԭĿ¼" + srcDirName + "�����ڣ�");
			return false;
		} else if (!srcDir.isDirectory()){
			System.out.println("�ƶ�Ŀ¼ʧ�ܣ�" + srcDirName + "����һ��Ŀ¼��");
			return false;
		}
		// ���Ŀ���ļ����������ļ��ָ�����β���Զ�����ļ��ָ���
		if (!destDirName.endsWith(File.separator)){
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		//���Ŀ���ļ��д��ڣ�
		if (destDir.exists()){
			if (overlay){
				//��������ɾ���Ѵ��ڵ�Ŀ��Ŀ¼
				System.out.println("Ŀ��Ŀ¼�Ѵ��ڣ�׼��ɾ������");
				if (!DeleteFileUtil.delete(destDirName)){
					System.out.println("�ƶ�Ŀ¼ʧ�ܣ�ɾ��Ŀ��Ŀ¼" + destDirName + "ʧ�ܣ�");
				}
			} else {
				System.out.println("�ƶ�Ŀ¼ʧ�ܣ�Ŀ��Ŀ¼" + destDirName + "�Ѵ��ڣ�");
				return false;
			}
		} else {
			//����Ŀ��Ŀ¼
			System.out.println("Ŀ��Ŀ¼�����ڣ�׼����������");
			if(!destDir.mkdirs()){
				System.out.println("�ƶ�Ŀ¼ʧ�ܣ�����Ŀ��Ŀ¼ʧ�ܣ�" );
				return false;
			}
		}
		boolean flag = true;
		//�ƶ�ԭĿ¼�µ��ļ�����Ŀ¼��Ŀ��Ŀ¼��
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			//�ƶ����ļ�
			if (files[i].isFile()){
				flag = MoveFileUtil.moveFile(files[i].getAbsolutePath(), 
						destDirName + files[i].getName(), overlay);
				if (!flag){
					break;
				}
			}
			//�ƶ���Ŀ¼
			else if (files[i].isDirectory()){
				flag = MoveFileUtil.moveDirectory(files[i].getAbsolutePath(), 
						destDirName + files[i].getName(), overlay);
				if (!flag){
					break;
				}
			}
		}
		if (!flag){
			System.out.println("�ƶ�Ŀ¼" + srcDirName + "��" + destDirName+ "ʧ�ܣ�");
			return false;
		}
		// ɾ��ԭĿ¼
		if (DeleteFileUtil.deleteDirectory(srcDirName)){
			System.out.println("�ƶ�Ŀ¼" + srcDirName + "��" + destDirName+ "�ɹ���");
			return true;
		} else {
			System.out.println("�ƶ�Ŀ¼" + srcDirName + "��" + destDirName+ "ʧ�ܣ�");
			return false;
		}
	}
	
	public static void main(String[] args) {
		//�ƶ������ļ������Ŀ���ļ����ڣ����滻
		String srcFileName = "C:/temp/temp.txt";
		String destFileName = "C:/tempbak/temp_bak.txt.";
		MoveFileUtil.moveFile(srcFileName, destFileName, true);
		System.out.println();
		//�ƶ�Ŀ¼�����Ŀ��Ŀ¼���ڣ��򲻸���
		String srcDirName = "C:/temp";
		String destDirName = "C:/tempbak";
		MoveFileUtil.moveDirectory(srcDirName, destDirName);
	}
}
