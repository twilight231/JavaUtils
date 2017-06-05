package com.zstax.util.常用类.number;

import java.util.Random;
/**
 * Javaʵ�ù�������е���java.util.Random�ṩ�˲�����������������ķ�����
 * �����Բ���int��long��float��double�Լ�Goussian�����͵��������
 * java.lang.Math�еķ���random()ֻ����double�͵��������
 */
public class RandomNumber {

	public static void main(String[] args) {

		// ʹ��java.lang.Math��random�������������
		System.out.println("Math.random(): " + Math.random());

		// ʹ�ò��������Ĺ��췽������java.util.Random����
		System.out.println("ʹ�ò��������Ĺ��췽�������Random����:");
		Random rd1 = new Random();
		// �����������͵������
		// �����ȷֲ���������
		System.out.println("int: " + rd1.nextInt());
		// �����ȷֲ�����������
		System.out.println("long: " + rd1.nextLong());
		// �����ȷֲ��������ڵ���0��С��1��float��[0, 1)
		System.out.println("float: " + rd1.nextFloat());
		// �����ȷֲ�����[0, 1)��Χ��double��
		System.out.println("double: " + rd1.nextDouble());
		// ����̬�ֲ����������
		System.out.println("Gaussian: " + rd1.nextGaussian());

		// ����һϵ�������
		System.out.print("�����������:");
		for (int i = 0; i < 5; i++) {
			System.out.print(rd1.nextInt() + "  ");
		}
		System.out.println();

		// ָ������������ķ�Χ
		System.out.print("[0,10)��Χ�������������: ");
		for (int i = 0; i < 10; i++) {
			// Random��nextInt(int n)��������һ��[0, n)��Χ�ڵ������
			System.out.print(rd1.nextInt(10) + "  ");
		}
		System.out.println();
		System.out.print("[5,23)��Χ�������������: ");
		for (int i = 0; i < 10; i++) {
			// ��ΪnextInt(int n)�����ķ�Χ�Ǵ�0��ʼ�ģ�
			// ������Ҫ������[5,28)ת����5 + [0, 23)��
			System.out.print(5 + rd1.nextInt(23) + "  ");
		}
		System.out.println();
		System.out.print("����nextFloat()����[0,99)��Χ�ڵ������������: ");
		for (int i = 0; i < 10; i++) {
			System.out.print((int) (rd1.nextFloat() * 100) + "  ");
		}
		System.out.println();
		System.out.println();

		// ʹ�ô������Ĺ��췽������Random����
		// ���캯���Ĳ�����long���ͣ�����������������ӡ�
		System.out.println("ʹ�ô������Ĺ��췽�������Random����:");
		Random ran2 = new Random(10);
		// ����������ͬ��Random�������ɵ������������һ���ġ�
		System.out.println("ʹ������Ϊ10��Random��������[0,10)�������������: ");
		for (int i = 0; i < 10; i++) {
			System.out.print(ran2.nextInt(10) + "  ");
		}
		System.out.println();
		Random ran3 = new Random(10);
		System.out.println("ʹ����һ������Ϊ10��Random��������[0,10)�������������: ");
		for (int i = 0; i < 10; i++) {
			System.out.print(ran3.nextInt(10) + "  ");
		}
		System.out.println();
		// ran2��ran3���ɵ������������һ���ģ����ʹ������û���������캯�����ɵ�Random����
		// �򲻻�������������������Ϊ��û���������캯�����ɵ�Random���������ȱʡ�ǵ�ǰϵͳʱ��ĺ�������

		// ���⣬ֱ��ʹ��Random�޷����������ظ������֣������Ҫ���ɲ��ظ�����������У���Ҫ��������ͼ�����
		//�����4�½��������������
	}
}
