package com.zstax.util.常用类.number;

/**
 * ���ֵķ�װ��
 * Ϊ�������û����ܻ���Ҫ�Զ���ķ�ʽ�����������ͣ���ˣ�
 * JavaΪÿ�ֻ����������Ͷ���������Ӧ�ķ�װ�ࡣ
 * byte --> Byte; short --> Short; int --> Integer
 * long --> Long; float --> Float; double --> Double
 * boolean --> Boolean; char --> Character 
 */
public class NumberClass {
	
	/** �������͵���װ���͵�ת�����Ի������͵�����Ϊ����newһ����Ӧ��װ��Ķ���
	 * ��װ���͵��������͵�ת�������ط�װ���Ͷ������Ӧ��valueֵ�� */
	
	/**
	 * byte��������ת����Byte���Ͷ���
	 */
	public static Byte byte2Byte(byte b){
		//return Byte.valueOf(b);
		return new Byte(b);
	}
	/**
	 * Byte���Ͷ���ת����byte��������
	 */
	public static byte Byte2byte(Byte B){
		if (B == null) {
			return 0;
		} else {
			return B.byteValue();
		}
	}
	/**
	 * int��������ת����Integer���Ͷ���
	 */
	public static Integer int2Integer(int i){
		// return Integer.valueOf(i);
		return new Integer(i);
	}
	/**
	 * Integer���Ͷ���ת����int��������
	 */
	public static int Integer2int(Integer integer){
		if (integer == null) {
			return 0;
		} else {
			return integer.intValue();
		}
	}
	//���������������װ���͵��໥ת�������������������Ͳ�һһ�г���
	
	public static void main(String[] args) {
		int i = 5;
		Integer I = int2Integer(i);
		//��int����ת����Integer֮�󣬿��Ա���ַ���
		String iStr = I.toString();//��int����ת����Integer֮�󣬿��Ա���ַ���
		Integer a = new Integer(5);
		Integer b = new Integer(10);
		//Integer�������ܽ��мӼ��˳������㣬����ʹ������intֵ��������
		int sum = a.intValue() + b.intValue();
	}
}
