package com.zstax.util.常用类.oo.factory;

import book.oo.sort.ISortNumber;
import book.oo.sort.impl.BubbleSort;
import book.oo.sort.impl.LinearInsertSort;
import book.oo.sort.impl.QuickSort;
import book.oo.sort.impl.SelectionSort;

/*
 * ģʽ���ƣ�����ģʽ
 * ģʽ������ͨ��һ��ͨ�õĽӿڴ�����ͬ�������
 * ģʽ��;������ӿڱ�̣���̬���࣬��̬��
 **/
public class Factory {
	public static final String SELECTION_SORT = "selection";
	public static final String BUBBLE_SORT = "bubble";
	public static final String LINEARINSERT_SORT = "liner";
	public static final String QUICK_SORT = "quick";

	public static ISortNumber getOrderNumber(String id) {
		if (SELECTION_SORT.equalsIgnoreCase(id)) {
			return new SelectionSort();
		} else if (BUBBLE_SORT.equalsIgnoreCase(id)) {
			return new BubbleSort();
		} else if (LINEARINSERT_SORT.equalsIgnoreCase("id")) {
			return new LinearInsertSort();
		} else if (QUICK_SORT.equalsIgnoreCase("id")) {
			return new QuickSort();
		} else {
			return null;
		}
	}
	/**
	 * �����������
	 * @param array	�����������
	 */
	public static void printIntArray(int[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		int[] intarray = new int[] { 6, 1, 3, 5, 4 };
		System.out.println("����ǰ�������ǣ�");
		printIntArray(intarray);
		System.out.println("��ѡ���������������н��������Ľ���ǣ�");
		int[] orderedArray = Factory.getOrderNumber(Factory.SELECTION_SORT)
				.sortASC(intarray);
		printIntArray(orderedArray);
		System.out.println("��ð�����򷨶�����������������Ľ���ǣ�");
		orderedArray = Factory.getOrderNumber(Factory.BUBBLE_SORT).sortASC(
				intarray);
		printIntArray(orderedArray);
		System.out.println("�����Բ������򷨶�����������������Ľ���ǣ�");
		orderedArray = Factory.getOrderNumber(Factory.LINEARINSERT_SORT)
				.sortASC(intarray);
		printIntArray(orderedArray);
		System.out.println("�ÿ������򷨶�����������������Ľ���ǣ�");
		orderedArray = Factory.getOrderNumber(Factory.QUICK_SORT).sortASC(
				intarray);
		printIntArray(orderedArray);
	}
}
