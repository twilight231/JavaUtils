package com.zstax.util.常用类.basic;

public class ChangeBasicType {

	/**
	 * �������͵��Զ�����
	 * �Զ���������
	 * 		1�����е�byte �ͺ�short�͵�ֵ�������� int ��
	 *		2�����һ����������long �ͣ��������ʽ����������long ��
	 * 		3�����һ����������float �ͣ��������ʽ����������float ��
	 * 		4�������һ����������double �ͣ�����������double ��
	 */
	private void typeAutoUpgrade() {
		byte b = 44;
		char c = 'b';
		short s = 1024;
		int i = 40000;
		long l = 12463l;
		float f = 35.67f;
		double d = 3.1234d;
		//(f * b)ʱ��b�Զ�����Ϊfloat���ͣ�(l * f)ʱ��l�Զ�����Ϊfloat��
		//(i / c)ʱ��c�Զ�����Ϊint�ͣ�(d * s)ʱ��s�Զ�����Ϊdouble
		//�����ʱ���м�������Ϊdouble���͡�����resultֻ������Ϊdouble���͡�
		//result����Ϊ�������ͻ�������ǣ�����ǿ������ת��
		double result = (f * b) + +(l * f) + (i / c) - (d * s);
		System.out.print((f * b) + " + " + (l * f) + " + " + (i / c) + " - "
				+ (d * s));
		System.out.println(" = " + result);
	}

	/**
	 * �������͵��Զ�ת��
	 * �Զ�ת�������������ǣ���1�����������Ǽ��ݵģ���2��Ŀ�����͵ķ�Χ��Դ���͵ķ�Χ��
	 * ��������£��������ǿ������ת��
	 */
	private void autoChange() {
		char c = 'a';
		byte b = 44;
		short s0 = b;
		int i0 = s0;
		int i1 = c;
		long l = i0;
		float f = l;
		double d = f;
		System.out.print("c = " + c + "; b = " + b + "; s0 = " + s0);
		System.out.print("; i0 = " + i0 + "; i1 = " + i1 + "; l = " + l);
		System.out.println("; f = " + f + "; d = " + d);

		//��float����ת����doubleʱ������ֲ���ȵ������������Ϊ��float�ķ�Χ��32λ����double�ķ�Χ��64λ���ڽ�������ת��ʱ��
		//������ʵ���϶��Ƕ����ƣ���Щʵ���ö������ܹ���ȷ��ʾ������Щʵ�������ö���λ�����ƶ��޷���ʾ�������������������ĸ���һ����
		//�Բ��ܹ���ȷ��ʾ��ʵ����������ת��ʱ������ֲ���ȵ������
		float fl = 1.7f;
		double dou = fl;
		System.out.println("fl = " + fl + "; dou = " + dou);//fl = 1.7; dou = 1.7000000476837158
		//���ǵ���һ������һ������ת��������һ�����ͣ���ת������ʱ��ֵ����һ����
		fl = (float)dou;
		System.out.println("fl = " + fl + "; dou = " + dou);
	}

	/**
	 * ǿ������ת��
	 * ���������Ͳ����ݣ�����Ŀ�����ͷ�Χ��Դ���ͷ�ΧСʱ���������ǿ������ת����
	 * ����ת��ʱ����� �ضϡ�ȡģ ������
	 */
	private void forceChange() {
		double d = 123.456d;
		float f = (float) d;
		long l = (long) d;
		int i = (int) d;
		short s = (short) d;
		byte b = (byte) d;

		System.out.print("d = " + d + "; f = " + f + "; l = " + l);
		System.out.println("; i = " + i + "; s = " + s + "; b = " + b);

		d = 567.89d;
		//�����ת�����Ƚ��нضϲ�������d��ֵ��Ϊ567������Ϊ567��byte�ķ�Χ256�������ǽ���ȡģ������
		//567��256ȡģ���ֵΪ55
		b = (byte) d;
		System.out.println("d = " + d + "; b = " + b);
	}

	public static void main(String[] args) {
		ChangeBasicType test = new ChangeBasicType();
		test.typeAutoUpgrade();
		test.autoChange();
		test.forceChange();
	}
//	fl = 1.7; dou = 1.7000000476837158
//	fl = 1.7; dou = 1.7000000476837158
}
