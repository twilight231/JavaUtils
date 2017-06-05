package com.zstax.util.常用类.arrayset;

import java.util.Comparator;

/**
 * �����Ƚ���������������������
 */
class MyIntComparator implements Comparator{

	/**
	 * o1��o2�󣬷���-1��o1��o2С������1��
	 */
	public int compare(Object o1, Object o2) {
		int i1 = ((Integer)o1).intValue();
		int i2 = ((Integer)o2).intValue();
		if (i1 < i2){
			return 1;
		}
		if (i1 > i2){
			return -1;
		}
		return 0;
	}
}
