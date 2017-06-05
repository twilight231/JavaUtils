package com.zstax.util.常用类.oo.sort.impl;

import book.oo.sort.ISortNumber;

/**
 * ʹ��ѡ�����򷨶����������������
 */
public class SelectionSort implements ISortNumber {
	public SelectionSort(){
	}
	/**
	 * ѡ������
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		//��ΪJava�Ĳ��������ǲ������ô�ֵ��ʽ����Ϊ������Ĺ����У���Ҫ�Ըı����飬
		//���ԣ�Ϊ�˱�֤���������ֵ���䣬��������������clone������ֱ�ӿ�¡һ�����顣
		int[] srcDatas = (int[]) intArray.clone();
		int size = srcDatas.length;
		for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				if (srcDatas[i] > srcDatas[j]) {
					swap(srcDatas, i, j);
				}
			}
		}
		return srcDatas;
	}
	/**
	 * �����������±�Ϊsrc��dest��ֵ
	 * @param data	����
	 * @param src	Դ�±�
	 * @param dest  Ŀ���±�
	 */
	private void swap(int[] data, int src, int dest) {
		int temp = data[src];
		data[src] = data[dest];
		data[dest] = temp;
	}

}
