package com.zstax.util.常用类.j2se5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ���ͱ��
 */
public class GeneralProgram {

	/**
	 * ʹ���ʺţ�ͨ������������κ����ͣ��������Ĳ����������κ����͵�Collection��
	 */
	public static void printCollection(Collection<?> collect){
		if (collect == null){
			return;
		}
		for(Object obj : collect){
			System.out.print(obj + "    ");
		}
		System.out.println();
	}
	/**
	 * ʹ�������Ƶ�ͨ�����? extends�������Խ����κ�Parent���������Collection
	 * @param collect
	 */
	public static void printNames(Collection<? extends Parent> collect){
		if (collect == null){
			return;
		}
		for(Parent parent : collect){
			System.out.print(parent.name + "    ");
		}
		System.out.println();
	}
	/**
	 * ���ͷ�������һ���������͵����飬��ӵ��б��С�
	 * @param <T> ����һ���������
	 * @param datas	�������
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] datas){
		if (datas == null){
			return null;
		}
		List<T> list_T = new ArrayList<T>();
		for (T x : datas){
			list_T.add(x);
		}
		return list_T;
	}
	/**
	 * ���ͷ�������һ��Parent�����в�������Ϊname��Parent����
	 * @param <T>	������Parent��������������
	 * @param parents	Parent������
	 * @param name	Ŀ��name
	 * @return	ƥ���Parent����
	 */
	public static <T extends Parent> T findParent(T[] parents, String name) {
		if (parents == null) {
			return null;
		}
		T parent = null;
		// ���α���Parent������
		for (T x : parents) {
			// ���Parent�����name�����nameƥ�䣬���˳�����
			if (x.name.equals(name)) {
				parent = x;
				break;
			}
		}
		// ����
		return parent;
	}
	
	public static void main(String[] args) {
		/** * ָ����������� ** */
		// ����һ������װ�ַ������б����б�ֻ��װ�ַ������͵Ķ���
		List<String> list_S = new ArrayList<String>();
		list_S.add("first");
		list_S.add("second");
		list_S.add("third");
		// ����װ��������
		Integer iObj = 10;
		// list_S.add(iObj);// error!!!
		// �ڴ��б���ȡֵʱ��������ǿ������ת����
		String firstS = list_S.get(0);
		String thirdS = list_S.get(2);
		System.out.println("firstS: " + firstS + "; thirdS: " + thirdS);

		/** **���ͺͼ̳�** */
		// String �̳� Object
		String s = "sss";
		Object o = s;
		// ��List<String>���̳�List<Object>
		// List<Object> list_O = list_S;// error!!!

		/** * ͨ��� *** */
		// ���þ��С�����ͨ����ķ���
		List<Integer> list_I = new ArrayList<Integer>();
		list_I.add(5555);
		list_I.add(6666);
		list_I.add(7777);
		// �÷����ȿ��Դ�ӡ�����б�Ҳ���Դ�ӡ�ַ����б�
		printCollection(list_I);
		printCollection(list_S);

		// ���þ��С�? extends�� ͨ����ķ���
		// ֻ���ܸ����Լ��������͵��б�
		List<Parent> list_Parent = new ArrayList<Parent>();
		list_Parent.add(new Parent("parentOne"));
		list_Parent.add(new Parent("parentTow"));
		list_Parent.add(new Parent("parentThree"));
		List<Child> list_Child = new ArrayList<Child>();
		list_Child.add(new Child("ChildOne", 20));
		list_Child.add(new Child("ChildTow", 22));
		list_Child.add(new Child("ChildThree", 21));
		printNames(list_Parent);
		printNames(list_Child);
		// ���ܽ����������͵Ĳ���
		// printNames(list_S);// error!!!

		/** * ���ͷ��� *** */
		// arrayToList�������������͵Ķ�����������Ӧ���б�
		Integer[] iObjs = { 55, 66, 77, 88, 99 };
		// ת����������
		List<Integer> result_I = arrayToList(iObjs);
		printCollection(result_I);
		String[] ss = { "temp", "temptemp", "hehe", "he", "hehehe" };
		// ת���ַ�������
		List<String> result_S = arrayToList(ss);
		printCollection(result_S);

		// findParent������һ��Parent�����и���name����Parent
		Parent[] parents = { new Parent("abc"), new Parent("bcd"),
				new Parent("def") };
		Parent parent = findParent(parents, "bcd");
		System.out.println("�ҵ���bcd��" + parent);
		Child[] children = { new Child("abc", 22), new Child("bcd", 23),
				new Child("def", 22) };
		Child child = findParent(children, "bcd");
		System.out.println("�ҵ���bcd��" + child);
		// ���ǲ������ַ��������н��в���
		// String sss = findParent(ss, "temp");// error!!!
	}
}
// ����
class Parent{
	public String name;
	public Parent(String name){
		this.name = name;
	}
	public String toString(){
		return "name = " + this.name;
	}
}
// ����
class Child extends Parent{
	public int age;
	public Child(String name, int age){
		super(name);
		this.age = age;
	}
	public String toString(){
		return super.toString() + ";  age = " + age;
	}
}