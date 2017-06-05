package com.zstax.util.常用类.oo.coverhide;

public class Parent {
	/** �������Parent����� */
	public static String kind = "book.oo.coverhide.Parent";
	/** �������Parent������ */
	public static int age = 50;
	/** ʵ��������Parent������ */
	public String name = "Parent";

	/** ��̬��������ȡParent����� */
	public static String getKind() {
		//��̬�����в�����ֻ���������
		System.out.println("Parent��getKind()�����������ˣ�");
		return kind;
	}
	/** ��̬��������ȡParent������ */
	public static int getAge() {
		System.out.println("Parent��getAge()�����������ˣ�");
		return age;
	}
	/** ʵ����������ȡParent������ */
	public String getName() {
		//ʵ�������в����Ŀ�����ʵ��������Ҳ�����������
		System.out.println("Parent��getName()�����������ˣ�");
		return this.name;
	}
	/** final��������Parent�������1*/
	public final int nextAge(){
		return ++age;
	}
}
