package com.zstax.util.常用类.oo.sort.impl;

import book.oo.sort.ISortNumber;
/**
 * ����ð�ݷ���������������
 */
public class BubbleSort implements ISortNumber {

	public BubbleSort() {
	}

	/**
	 * ð������
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		//��ΪJava�Ĳ��������ǲ������ô�ֵ��ʽ����Ϊ������Ĺ����У���Ҫ�Ըı����飬
		//���ԣ�Ϊ�˱�֤���������ֵ���䣬��������������clone������ֱ�ӿ�¡һ�����顣
		int[] srcDatas = (int[]) intArray.clone();
		boolean changedPosition = true;
		int comparedTimes = 0;
		int maxComparedTimes = srcDatas.length - 1;

		while ((comparedTimes < maxComparedTimes) && (changedPosition)) {
			for (int i = 0; i < (maxComparedTimes - comparedTimes); i++) {
				changedPosition = false;
				if (srcDatas[i] > srcDatas[i + 1]) {
					swap(srcDatas, i, i + 1);
					changedPosition = true;
				}
			}
			comparedTimes++;
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
