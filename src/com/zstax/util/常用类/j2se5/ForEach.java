package com.zstax.util.常用类.j2se5;

import java.util.ArrayList;
import java.util.List;

/**
 * �µ�forѭ������ʽΪfor(type x: type y)��
 * ��ʾ���α�������򼯺�y��Ԫ�أ���Ԫ��ֵ����x��
 * ע�⣬
 * (1)ֻ�ܱ�����ȡ����Ԫ�أ������޸������Ԫ�أ����޸�x��ֵ������û��Ӱ�죻
 * (2)���������Σ��������ű�����
 */
public class ForEach {
	/**
	 * �������������
	 */
	public static long getSum(int[] nums) throws Exception{
		if (nums == null){
			throw new Exception("����Ĳ������룬����Ϊnull��");
		}
		long sum = 0;
		// ����ȡ��numsԪ�ص�ֵ���ۼ�
		for (int x : nums){
			sum += x;
		}
		return sum;
	}
	/**
	 * �������б����
	 * @param nums
	 * @return
	 * @throws Exception
	 */
	public static long getSum(List<Integer> nums) throws Exception{
		if (nums == null){
			throw new Exception("����Ĳ������룬����Ϊnull��");
		}
		long sum = 0;
		// ���Ը���������һ���ķ�ʽ�����б�
		for (int x : nums){
			sum += x;
		}
		return sum;
	}
	/**
	 * ���ά�����ƽ��ֵ
	 * @param nums
	 * @return
	 * @throws Exception
	 */
	public static int getAvg(int[][] nums) throws Exception{
		if (nums == null){
			throw new Exception("����Ĳ������룬����Ϊnull��");
		}
		long sum = 0;
		long size = 0;
		// ���ڶ�ά���飬ÿ������Ԫ�ض���һά����
		for (int[] x : nums){
			// һά�����е�Ԫ�ز�������
			for (int y : x){
				sum += y;
				size ++;
			}
		}
		return (int)(sum/size);
	}

	public static void main(String[] args) throws Exception {
		int[] nums = {456, 23, -739, 163, 390};
		List<Integer> list_I = new ArrayList<Integer>();
		for (int i=0; i<5; i++){
			list_I.add(nums[i]);
		}
		System.out.println(getSum(nums));
		System.out.println(getSum(list_I));
		int[][] numss = {{1,2,3}, {4,5,6}, {7,8,9,10}};
		System.out.println(getAvg(numss));
	}
}
