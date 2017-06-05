package com.zstax.util.常用类.arrayset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ��List�е�Ԫ������
 */
public class SortList {
	
	public static void output(List list){
		if (list == null){
			return;
		}
		for (int i=0; i<list.size(); i++){
			System.out.print(list.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(new Integer(5));
		list.add(new Integer(8));
		list.add(new Integer(1));
		list.add(new Integer(3));
		list.add(new Integer(2));
		list.add(new Double(3.1));
		System.out.println("list��ʼ״̬");
		SortList.output(list);
		//Collections.sort��������Ĭ�ϱȽ�������list��Ԫ��
		Collections.sort(list);
		System.out.println("list��Ĭ�ϱȽ���������״̬");
		SortList.output(list);
		//���潫list��Ԫ�ذ���������
		Collections.sort(list, new MyIntComparator());
		System.out.println("list���Զ���Ƚ���������״̬");
		SortList.output(list);
		
		//��ˣ����������Զ�����Ķ��󣬵������ڼ����������к������Ҫ�����ǽ�������
		//��Ҫ�Լ��ṩ��Ӧ���Զ�����ıȽ������Զ���Ƚ�������ʵ��Comparator�ӿڡ�
		//Ȼ�����Collections.sort(list, comparator);������������������
	}
}

