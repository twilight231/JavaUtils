package com.zstax.util.常用类.arrayset;

import java.util.Arrays;

/**
 * ʹ��java.util.Arrays���������
 */
public class UsingArrays {
	/**
	 * �����������
	 * @param array
	 */
	public static void output(int[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {

		// �������
		int[] array0 = new int[5];
		// ��array0������Ԫ�ص�ֵ����Ϊ5
		Arrays.fill(array0, 5);
		System.out.println("����Arrays.fill(array0, 5)��: ");
		UsingArrays.output(array0);
		// ��array0�еĵ�2������3��Ԫ�ص�ֵ��Ϊ8
		Arrays.fill(array0, 2, 4, 8);
		System.out.println("����Arrays.fill(array0, 2, 4, 8)��: ");
		UsingArrays.output(array0);

		// ����������
		int[] array1 = new int[] { 7, 8, 3, 12, 6, 3, 5, 4 };
		// ������ĵ�2������6��Ԫ�ؽ�������
		Arrays.sort(array1, 2, 7);
		System.out.println("����Arrays.sort(array1, 2, 7)��: ");
		UsingArrays.output(array1);
		// �����������������
		Arrays.sort(array1);
		System.out.println("����Arrays.sort(array1)��: ");
		UsingArrays.output(array1);

		// �Ƚ�����Ԫ���Ƿ����
		System.out.println(Arrays.equals(array0, array1));
		int[] array2 = (int[]) array1.clone();
		System.out.println("array1 �� array2�Ƿ���ȣ� "
				+ Arrays.equals(array1, array2));

		// ʹ�ö��ַ��������в���ָ��Ԫ�����ڵ��±꣨���ص��±��1��ʼ��
		// ����������ź���ģ��������᲻��ȷ
		Arrays.sort(array1);
		System.out.println("Ԫ��8��array1�е�λ��: " + Arrays.binarySearch(array1, 8));
		// ��������ڣ��ͷ��ظ���
		System.out.println("Ԫ��9��array1�е�λ��: " + Arrays.binarySearch(array1, 9));
	}
}
