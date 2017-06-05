package com.zstax.util.常用类.oo.sort.impl;

import book.oo.sort.ISortNumber;
/**
 * �������Բ�������ʵ�����������
 */
public class LinearInsertSort implements ISortNumber {
	public LinearInsertSort(){
	}
	/**
	 * ���Բ��뷨
	 */
	public int[] sortASC(int[] intArray) {
		if (intArray == null){
			return null;
		}
		int[] srcDatas = (int[]) intArray.clone();
        int size = srcDatas.length;
        int temp = 0;
        int index = 0;
        //�ٶ���һ���������Ѿ��ź������У�����i�Ǵ�1��ʼ�����Ǵ�0��ʼ��
        for (int i=1; i<size; i++){
            temp = srcDatas[i];
            index = i;
            while ((index > 0) && (temp < srcDatas[index-1])){
                //�ƶ�index���������
                srcDatas[index] = srcDatas[index-1];
                index--;
            }
            srcDatas[index] = temp;
        }
        return srcDatas;
	}
}
