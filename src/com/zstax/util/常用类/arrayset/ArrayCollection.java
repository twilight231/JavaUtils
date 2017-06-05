package com.zstax.util.常用类.arrayset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ������Collection֮���ת��
 */
public class ArrayCollection {

	public static void main(String[] args) {
		
		List list = new ArrayList();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		//��List�е��������Ͷ�һ��ʱ�����Խ�listת��������
		//ת���ɶ�������ʱ��ֱ�ӵ���toArray����
		Object[] objArray = list.toArray();
		System.out.println("��listת���ɵĶ�������ĳ���Ϊ: " + objArray.length);
		//��ת�����������͵�����ʱ����Ҫǿ������ת�������ң�Ҫʹ�ô�������toArray������
		//toArray�����Ĳ���Ϊһ���������飬��list�е����ݷ������������
		//����������ĳ���С��list��Ԫ�ظ���ʱ�����Զ���������ĳ�������Ӧlist�ĳ���
		String[] strArray1 = (String[])list.toArray(new String[0]);
		System.out.println("��listת���ɵ��ַ�������ĳ���Ϊ: " + strArray1.length);
		//����һ��������list�ĳ�����ȵ��ַ������顣
		String[] strArray2 = (String[])list.toArray(new String[list.size()]);
		System.out.println("��listת���ɵ��ַ�������ĳ���Ϊ: " + strArray2.length);
		list.clear();//���List
		
		//������ת����List
		//�����ӵ�List
		for (int i=0; i<objArray.length; i++){
			list.add(objArray[i]);
		}
		System.out.println("������ת���ɵ�list��Ԫ�ظ���: " + objArray.length);
		list.clear();//���List
		//ֱ��ʹ��Arrays���asList����
		list = Arrays.asList(objArray);
		System.out.println("������ת���ɵ�list��Ԫ�ظ���: " + objArray.length);
		
		Set set = new HashSet();
		set.add("aaa");
		set.add("bbb");
		//Setת��������
		objArray = set.toArray();
		strArray1 = (String[])set.toArray(new String[0]);
		strArray2 = (String[])set.toArray(new String[set.size()]);
		
		//����ת����Set
		//������ת����List������List����Set
		set = new HashSet(Arrays.asList(objArray));
		//��Set��գ�Ȼ�������ת���ɵ�Listȫ��add
		set.clear();
		set.addAll(Arrays.asList(strArray1));
	}
}
