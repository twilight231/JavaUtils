package com.zstax.util.常用类.oo.coverhide;

public class Child extends Parent {

	/** �������Child����� */
	public static String kind = "book.oo.coverhide.Child";
	/** ʵ��������Child������*/
	public int age = 25;// age������Parent������static�ġ�
	/** ʵ��������Child������ */
	public String name = "Child";
	/** ��̬��������ȡChild����� */
	public static String getKind() {
		System.out.println("Child��getKind()�����������ˣ�");
		return kind;
	}
	/** ��̬��������ȡ���������*/
	public static String getParentKind(){
		//ͨ��������"."���ʸ����б����ص������
		return Parent.kind;
	}
	/** ʵ����������ȡChild������ */
	public String getName() {
		System.out.println("Child��getName()�����������ˣ�");
		return this.name;
	}
	/** ʵ����������ȡ���������*/
	public String getParentName(){
		//ͨ��super�ؼ��ָ����б����ص�ʵ������
		return super.name;
	}
//	����ʵ���������ܹ����Ǹ���ľ�̬����
//	public int getAge(){
//		return this.age;
//	}
//	���󣡸����final�������ܹ�������
//	public int nextAge(){
//		return ++age;
//	}
}
