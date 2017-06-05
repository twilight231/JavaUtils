package com.zstax.util.常用类.arrayset;

/**
 * ��̬��������ĳ���
 */
public class AdjustArrayLength {
	private static int DEFAULT_LENGTH = 10; 
	
	public static Integer[] increase(Integer[] src){
		return increase(src, DEFAULT_LENGTH);
	}
	public static Integer[] increase(Integer[] src, int length){
		if (src == null){
			return null;
		}
		//�½�һ�����飬����Ϊ������ĳ��ȼ���length
		Integer[] result = new Integer[src.length + length];
		//�������ǰ�沿�ֵ�ֵ��������ֵһ��
		//����һ�ֳ��õĿ�������ķ���
		System.arraycopy(src, 0, result, 0, src.length);
		return result;
	}
	
	public static void main(String[] args) {
		Integer[] array = new Integer[10];
		for (int i=0; i<10; i++){
			array[i] = new Integer(i);
		}
		//��������ĳ���
		Integer[] newArray = AdjustArrayLength.increase(array);
		//�������
		newArray[10] = new Integer(11);
	}
}
