package com.zstax.util.常用类.oo.sort;

import book.oo.sort.impl.BinaryInsertSort;
import book.oo.sort.impl.BubbleSort;
import book.oo.sort.impl.LinearInsertSort;
import book.oo.sort.impl.QuickSort;
import book.oo.sort.impl.SelectionSort;

public class SortTest {
	
	/**
	 * ��ӡ����
	 * @param intArray  ����ӡ������
	 */
	public static void printIntArray(int[] intArray){
		if (intArray == null){
			return;
		}
		for (int i=0; i<intArray.length; i++){
			System.out.print(intArray[i] + " ");
		}
		System.out.println();
	}
	public static void main(String[] args){
		int[] intArray = new int[]{6,3,4,2,7,2,-3,3};
		
		System.out.print("����ǰ�����飺");
		printIntArray(intArray);
		ISortNumber test = new SelectionSort();
		System.out.print("ѡ��������������");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("����ǰ�����飺");
		printIntArray(intArray);
		test = new BubbleSort();
		System.out.print("ð��������������");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("����ǰ�����飺");
		printIntArray(intArray);
		test = new LinearInsertSort();
		System.out.print("���Բ���������������");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("����ǰ�����飺");
		printIntArray(intArray);
		test = new BinaryInsertSort();
		System.out.print("���ֲ���������������");
		printIntArray(test.sortASC(intArray));
		
		System.out.print("����ǰ�����飺");
		printIntArray(intArray);
		test = new QuickSort();
		System.out.print("����������������");
		printIntArray(test.sortASC(intArray));
	}
//	����ǰ�����飺6 3 4 2 7 2 -3 3 
//	ѡ��������������-3 2 2 3 3 4 6 7 
//	����ǰ�����飺6 3 4 2 7 2 -3 3 
//	ð��������������2 2 -3 3 3 4 6 7 
//	����ǰ�����飺6 3 4 2 7 2 -3 3 
//	���Բ���������������-3 2 2 3 3 4 6 7 
//	����ǰ�����飺6 3 4 2 7 2 -3 3 
//	���ֲ���������������-3 2 2 3 3 4 6 7 
//	����ǰ�����飺6 3 4 2 7 2 -3 3 
//	����������������-3 2 2 3 3 4 6 7 
}
