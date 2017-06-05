package com.zstax.util.src.demo.others;

import java.util.Arrays;

public class ArraysDemo {
	/*
	 * 1.�������ṩ�������ܣ��Ի�����������length<7����ֱ�Ӳ������򣬷�����ÿ������� �������Ԫ��ʱ���󣬲��úϲ�����
	 * 2.�ṩ���ֲ��ҷ�ʵ�֣�ע����ֲ���ʱ�ȶ�����������򣬷��򷵻�һ����ȷ��ֵ
	 */
	public static void main(String[] args) {
		int[][] a = { { 1, 2 } };
		int[][] b = { { 1, 2 } };
		System.out.println(Arrays.toString(a));
		// 3. ��ά�����toString
		System.out.println(Arrays.deepToString(a));
		System.out.println(Arrays.hashCode(a));
		System.out.println(Arrays.deepHashCode(a));
		System.out.println(Arrays.equals(a, b));
		// 4. ��ά����ıȽ�
		System.out.println(Arrays.deepEquals(a, b));

		// 5. ��û��ʵ�ֶ�ά����ĸ���
		int[][] c = Arrays.copyOf(a, 1);
		// 6.�������
		Arrays.fill(a[0], 5);// �ڴ˸ı�a��ֵӰ�쵽������c��ֵ
		System.out.println(Arrays.deepToString(c));
		System.out.println(Arrays.equals(a, c));
		System.out.println(Arrays.deepEquals(a, c));

	}
}
