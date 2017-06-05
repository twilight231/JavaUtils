package com.zstax.util.常用类.oo.sort.impl;

import book.oo.sort.ISortNumber;
/**
 * ���ÿ�������ʵ�����������
 */
public class QuickSort implements ISortNumber {

	public QuickSort(){
	}
	/**
	 * ��������
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		int[] srcDatas = (int[]) intArray.clone();
		return this.quickSort(srcDatas, 0, srcDatas.length - 1);
	}
	/**
	 * ���÷��εݹ�ķ���ʵ�ֿ�������
	 * @param srcDatas	�����������
	 * @param first		��ʼ�±�
	 * @param last		��ֹ�±�
	 * @return
	 */
	private int[] quickSort(int[] srcDatas, int first, int last) {
		if (first < last) {
			int pos = partition(srcDatas, first, last);
			quickSort(srcDatas, first, pos - 1);
			quickSort(srcDatas, pos + 1, last);
		}
		return srcDatas;
	}
	/**
	 * Ѱ������ķ��ε㣬
	 * ��������ĵ�һ�������Σ��ѱ������һ������������ţ��ѱ������һ����С����ǰ��
	 * @param srcDatas
	 * @param first
	 * @param last
	 * @return	��������ĵ�һ�����������±�
	 */
	private int partition(int[] srcDatas, int first, int last) {
		int temp = srcDatas[first];
		int pos = first;
		for (int i = first + 1; i <= last; i++) {
			if (srcDatas[i] < temp) {
				pos++;
				swap(srcDatas, pos, i);
			}
		}
		swap(srcDatas, first, pos);
		return pos;
	}
	/**	�����������±�Ϊsrc��dest��ֵ*/
	private void swap(int[] data, int src, int dest) {
		int temp = data[src];
		data[src] = data[dest];
		data[dest] = temp;
	}
}
