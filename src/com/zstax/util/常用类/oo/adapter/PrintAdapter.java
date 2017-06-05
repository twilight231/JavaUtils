package com.zstax.util.常用类.oo.adapter;

import book.oo.factory.Factory;
import book.oo.sort.ISortNumber;

public class PrintAdapter extends Printer implements ISortNumber{

	private ISortNumber mySort;
	public PrintAdapter(ISortNumber sort){
		super();
		this.mySort = sort;
	}
	
	public int[] sortASC(int[] intArray) {
		if (this.mySort != null){
			return this.mySort.sortASC(intArray);
		} else {
			return null;
		}
	}
	
	public static void main(String[] args){
		int[] array = new int[]{7,9,4,6,2,-1,12};
		PrintAdapter adapter = new PrintAdapter(Factory.getOrderNumber(Factory.BUBBLE_SORT));
		adapter.printIntArray(adapter.sortASC(array));
	}
	
	/**
	 * Adapterģʽ������������ģʽ��ʹ����ԭ��û�й���������һ��ʹ�á�
ƽʱ���ǻᾭ��������������������������ֳɵ��࣬����֮��û��ʲô��ϵ�������������ڼ���������һ����ķ�����
ͬʱҲ��������һ����ķ�������һ����������ǣ��޸����Ǹ��ԵĽӿڣ��������������Ը�⿴���ġ����ʱ��Adapterģʽ�ͻ������ó��ˡ�
	 * 
	 * �����Ѿ�ʵ����һ����������Ľӿڣ�Ҳ����һ���ܹ���ӡ������࣬�ٶ������޸�ԭ�е�ʵ�֡�
	 * ���ڣ���Ҫʵ��һ���࣬���ܹ���ӡ���飬���ܶ�����������򡣴�ʱ����Ҫ��Adapterģʽ��ʵ���ˡ�
	 * 
	 * ���壺Adapter����һ����Ľӿ�ת���ɿͻ�ϣ��������һ���ӿڡ�Adapterģʽʹ��ԭ�����ڽӿڲ����ݶ�����һ��������Щ�����һ������
	 */
}
