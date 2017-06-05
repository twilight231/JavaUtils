package com.zstax.util.常用类.arrayset;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * ��Map�е�Ԫ�ؽ�������
 */
public class SortMap {

	public static void main(String[] args) {
		Map myMap = new HashMap();
		myMap.put(new Integer(5), "aaa");
		myMap.put(new Integer(8), "bbb");
		myMap.put(new Integer(4), "ccc");
		myMap.put(new Integer(7), "ddd");
		myMap.put(new Integer(3), "eee");
		System.out.println("��ʼ�����myMap: ");
		TestMap.output(myMap);
		
		//����TreeMap�������ܽ�myMap����
		Map treeMap = new TreeMap(myMap);
		System.out.println("������myMap: ");
		TestMap.output(treeMap);
		//�����Զ���ıȽ�������
		TreeMap newTreeMap = new TreeMap(new MyIntComparator());
		newTreeMap.putAll(myMap);//��һ��Map���������ݷ��ڱ�Map��
		System.out.println("ʹ���Զ���Ƚ���������myMap: ");
		TestMap.output(newTreeMap);
	}
}
