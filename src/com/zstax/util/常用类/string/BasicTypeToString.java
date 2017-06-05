package com.zstax.util.常用类.string;

/**
 * �����������ַ����Ļ���ת��
 */
public class BasicTypeToString{
	/**
	 * ����ת�����ַ��� 
	 * @param n	��ת��������
	 */
	public String int2str(int n){
		//4�ַ���ʵ��ת����
		//return new Integer(n).toString();
		//return "" + n;
		//return Integer.toString(n);
		return String.valueOf(n);
	}
	/**
	 * �ַ���ת��������
	 * @param str	��ת�����ַ���
	 */
	public int str2int(String str){
		//2�ַ���ʵ��ת��
		//return new Integer(str).intValue();
		return Integer.parseInt(str);
	}
	public String double2str(double d){
		//return new Double(d).toString();
		//return "" + d;
		//return Double.toString(d);
		return String.valueOf(d);
		
	}
	public double str2double(String str){
		//return new Double(str).doubleValue();
		return Double.parseDouble(str);
	}
	//���������������ַ����Ļ���ת�����ﲻһһ�г�������������

	public static void main(String[] args) {
		BasicTypeToString test = new BasicTypeToString();
		int n = 156;
		String strI = test.int2str(n);
		System.out.println("test.int2str(n) = " + strI );
		System.out.println("test.str2int(strI) = " + test.str2int(strI));
		
		double d = 12.345;
		String strD = test.double2str(d);
		System.out.println("test.double2str(d) = " + strD );
		System.out.println("test.str2double(strD) = " + test.str2double(strD));
	}
}

