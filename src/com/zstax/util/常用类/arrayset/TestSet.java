package com.zstax.util.常用类.arrayset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * ��ʾ����Set��ʹ��
 * ����Set��ÿ��Ԫ�ر�����Ψһ�ģ���ΪSet�������ظ�Ԫ�ء�
 */
public class TestSet {

	/**
	 * ��ʼ��Set��Ԫ��
	 * @param set
	 */
	public static void init(Set set){
		if (set != null){
			set.add("aaa");
			set.add("ccc");
			set.add("bbb");
			set.add("eee");
			set.add("ddd");
		}
	}
	/**
	 * ���set��Ԫ��
	 * @param set
	 */
	public static void output(Set set){
		if (set != null){
			//ʹ�õ���������Set��Ҳֻ����һ�ַ���
			Iterator it = set.iterator();
			while (it.hasNext()){
				System.out.print(it.next() + " ");
			}
		}
		System.out.println();
	}
	/**
	 * ʹ��HashSet
	 */
	public static void testHashSet(){
		Set mySet = new HashSet();
		init(mySet);
		System.out.println("ʹ��HashSet: ");
		output(mySet);
	}
	/**
	 * ʹ��TreeSet
	 */
	public static void testTreeSet(){
		Set mySet = new TreeSet();
		init(mySet);
		System.out.println("ʹ��TreeSet: ");
		output(mySet);
	}
	/**
	 * ʹ��LinkedHashSet
	 */
	public static void testLinkedHashSet(){
		Set mySet = new LinkedHashSet();
		init(mySet);
		System.out.println("ʹ��LinkedHashSet: ");
		output(mySet);
	}
	public static void main(String[] args) {
		TestSet.testHashSet();
		TestSet.testTreeSet();
		TestSet.testLinkedHashSet();
		
		Set mySet = new HashSet();
		init(mySet);
		//Set������Ԫ���ظ�
		mySet.add("aaa");
		mySet.add("bbb");
		System.out.println("ΪmySet����aaa, bbbԪ�غ�: ");
		output(mySet);
		//ɾ��Ԫ��
		mySet.remove("aaa");
		System.out.println("mySetɾ��aaaԪ�غ�: ");
		output(mySet);
		//��������һ�������е�����Ԫ��
		List list = new ArrayList();
		list.add("aaa");
		list.add("aaa");
		list.add("fff");
		mySet.addAll(list);
		System.out.println("mySet�������һ�����ϵ�����Ԫ�غ�: ");
		output(mySet);
		//ɾ����������һ�����ϰ��������������Ԫ��
		mySet.retainAll(list);
		System.out.println("mySetɾ����������һ�����ϰ��������������Ԫ�غ�: ");
		output(mySet);
		//ɾ������һ�����ϰ���������Ԫ��
		mySet.removeAll(list);
		System.out.println("mySetɾ������һ�����ϰ���������Ԫ�غ�: ");
		output(mySet);
		//��ȡSet��Ԫ�صĸ���
		System.out.println("mySet�е�ǰԪ�صĸ���: " + mySet.size());
		//�ж�Set��Ԫ�ظ����Ƿ�Ϊ0
		System.out.println("mySet�е�ǰԪ��Ϊ0��  " + mySet.isEmpty());
		
		/**
		 * ��1��Set�������ظ�Ԫ�أ���˼���Set��Object���붨��equals()������ȷ�������Ψһ�ԡ�
		 * ��2��HashSet����ɢ�к�����Ԫ�ؽ���������ר��Ϊ���ٲ�ѯ����Ƶġ�����HashSet�Ķ�����붨��hashCode()��
		 * ��3��TreeSet���ú���������ݽṹ��������Ԫ�أ��ܱ�֤Ԫ�صĴ���ʹ�������Դ�Set����ȡ��������С�
		 * ��Ҫע����ǣ������Լ�����ʱ��Set��Ҫά��Ԫ�صĴ洢˳�����Ҫʵ��Comparable�ӿڲ�����compareTo()������
		 * ��4��LinkedHashSet�ڲ�ʹ��ɢ���Լӿ��ѯ�ٶȣ�ͬʱʹ������ά��Ԫ�صĲ���Ĵ�����ʹ�õ���������Setʱ������ᰴԪ�ز���Ĵ�����ʾ��
	 */
	}
}
