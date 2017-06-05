package com.zstax.util.常用类.j2se5;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * ͨ��ProcessBuilderִ�б�������������ڴ�������ϵͳ���̡�
 * ��ȡ������MAC��ַ
 * 
 * ÿ������������������Щ�������ԣ�
 * (1)���� ��һ���ַ����б�����ʾҪ���õ��ⲿ�����ļ��������������У�
 * (2)���� �Ǵӱ��� ��ֵ ��������ϵͳ��ӳ�䡣��ʼֵ�ǵ�ǰ���̻�����һ������.
 * (3)����Ŀ¼��Ĭ��ֵ�ǵ�ǰ���̵ĵ�ǰ����Ŀ¼��ͨ������ϵͳ���� user.dir ������.
 * (4)redirectErrorStream ���ԡ������������Ϊ false��
 * ��˼���ӽ��̵ı�׼����ʹ�����������͸���������������
 * ��Щ������ͨ�� Process.getInputStream() �� Process.getErrorStream() ���������ʡ�
 * �����ֵ����Ϊ true����׼�������׼����ϲ�����ʹ�ù���������Ϣ����Ӧ�������ø����ס�
 * �ڴ�����£��ϲ������ݿɴ� Process.getInputStream() ���ص�����ȡ��
 * ���� Process.getErrorStream() ���ص�����ȡ��ֱ�ӵ����ļ�β��
 */
public class UsingProcessBuilder {

	/**
	 * ��ȡWindowsϵͳ�µ�������MAC��ַ
	 * @return
	 */
	public static List<String> getPhysicalAddress() {
		Process p = null;
		//���������б� 
		List<String> address = new ArrayList<String>();

		try {
			//ִ��ipconfig /all���� 
			p = new ProcessBuilder("ipconfig", "/all").start();
		} catch (IOException e) {
			return address;
		}
		byte[] b = new byte[1024];
		int readbytes = -1;
		StringBuffer sb = new StringBuffer();
		//��ȡ�������ֵ 
		InputStream in = p.getInputStream();
		try {
			while ((readbytes = in.read(b)) != -1) {
				sb.append(new String(b, 0, readbytes));
			}
		} catch (IOException e1) {
		} finally {
			try {
				in.close();
			} catch (IOException e2) {
			}
		}
		//���·������ֵ���õ��������� 
		String rtValue = sb.toString();
		int i = rtValue.indexOf("Physical Address. . . . . . . . . :");
		while (i > 0) {
			rtValue = rtValue.substring(i
					+ "Physical Address. . . . . . . . . :".length());
			address.add(rtValue.substring(1, 18));
			i = rtValue.indexOf("Physical Address. . . . . . . . . :");
		}
		
		return address;
	}
	/**
	 * ִ���Զ����һ��������������C:/temp�£�������Ҫ2������������֧�֡�
	 */
	public static boolean executeMyCommand(){
		// ����ϵͳ���̴�����
		ProcessBuilder pb = new ProcessBuilder("myCommand", "myArg1", "myArg2");
		// ��ý��̵Ļ���
		Map<String, String> env = pb.environment();
		// ���ú�ȥ����������
		env.put("VAR1", "myValue");
		env.remove("VAR0");
		env.put("VAR2", env.get("VAR1") + ";");
		// �л�����Ŀ¼
		pb.directory(new File("C:/temp"));
		try {
			// �õ�����ʵ��
			Process p = pb.start();
			// �ȴ��ý���ִ�����
			if (p.waitFor() != 0){
				// ����������н����Ϊ0����ʾ�����Ǵ����˳���
				// ��ý���ʵ���Ĵ������
				InputStream error = p.getErrorStream();
				// do something
			}
			// ��ý���ʵ���ı�׼���
			InputStream sdin = p.getInputStream();
			// do something
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
		return true;
	}
	
	public static void main(String[] args) {
		List<String> address = UsingProcessBuilder.getPhysicalAddress();
		for (String add : address) {
			System.out.printf("����������ַ: %s%n", add);
		}
	}
}