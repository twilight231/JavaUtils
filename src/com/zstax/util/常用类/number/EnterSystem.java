package com.zstax.util.常用类.number;

/**
 * �������ֵĽ��ƣ��а˽��ơ�ʮ���ƺ�ʮ������
 */
public class EnterSystem {

	public static void main(String[] args) {

		//�˽������ֵ���������ǰ�����0(��)
		int iOct = 0567;
		//ʮ���Ƶ�����
		int iTen = 1000;
		//ʮ���������ֵ���������ǰ�����0x(��x)��x�����ִ�Сд		
		int iHex = 0xABCD;

		//�˽���ת���ɶ�����
		System.out.print("�˽���0567ת���ɶ����ƣ�");
		System.out.print(Integer.toString(iOct, 2) + "; ");//101110111
		System.out.println(Integer.toBinaryString(iOct));//101110111
		//�˽���ת����ʮ����
		System.out.print("�˽���0567ת����ʮ���ƣ�");
		System.out.print(Integer.toString(iOct, 10) + "; ");//375
		System.out.println(Integer.toString(iOct));
		//�˽���ת����ʮ������
		System.out.print("�˽���0567ת����ʮ�����ƣ�");
		System.out.print(Integer.toString(iOct, 16) + "; ");//177
		System.out.println(Integer.toHexString(iOct));
		//������ת������������
		System.out.print("�˽���0567ת�����߽��ƣ�");
		System.out.println(Integer.toString(iOct, 7));//1044

		//ͬ�����Խ�ʮ���ơ�ʮ������ת��������������Ƶ�����
		System.out.print("ʮ����1000ת����ʮ�����ƣ�");
		System.out.print(Integer.toString(iTen, 16) + "; ");//3e8
		System.out.println(Integer.toHexString(iTen));
		System.out.print("ʮ����1000ת���ɰ˽��ƣ�");
		System.out.println(Integer.toOctalString(iTen));
		System.out.print("ʮ������0xABCDת����ʮ���ƣ�");
		System.out.println(Integer.toString(iHex, 10));//43981
		System.out.print("ʮ������0xABCDת���ɶ����ƣ�");
		System.out.print(Integer.toBinaryString(iHex) + "; ");
		System.out.println(Long.toBinaryString(iHex));

		//Java�����ͷ�װ��Integer��Long�ṩtoString(int i, int radix)��̬������
		//���Խ�һ��������Ƶ�����ת��������������Ƶ�����
		//������������֮�⣬����ת���ɶ����ƣ�����ʹ��toBinaryString(int i)����
		//����ת���ɰ˽��ƣ�����ʹ��toOctalString(int i)����
		//����ת����ʮ�����ƣ�����ʹ��toHexString(int i)������
		//����������������ʵ�ֶ�ʹ����toString(int i, int radix)��
	}
}
