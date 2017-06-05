package com.zstax.util.常用类.reflect;

/**
 * instance of ���ڼ���������͡�
 * ��1����Ķ���������instance of���������Ϊtrue
 * ��2����������븸����instance of���������Ϊtrue��
 *  ��ˣ����ж�����Object��instance of�����������Ϊtrue��
 * ��3����������£������Ϊfalse��
 */
public class InstanceOf {
	
	// ����
	static class ClassA {
	}
	// ����
	static class ClassB extends ClassA{
	}

	public static void main(String[] args) {
		ClassA a = new ClassA();
		ClassB b = new ClassB();
		// ������a��b�Ƿ�ΪClassA����
		if (a instanceof ClassA){
			System.out.println("Object a is a ClassA Object!");
		} else {
			System.out.println("Object a is not a ClassA Object!");
		}
		if (b instanceof ClassA){
			System.out.println("Object b is a ClassA Object!");
		} else {
			System.out.println("Object b is not a ClassA Object!");
		}
		
		// ������a��b�Ƿ�ΪClassB����
		if (a instanceof ClassB){
			System.out.println("Object a is a ClassB Object!");
		} else {
			System.out.println("Object a is not a ClassB Object!");
		}
		if (b instanceof ClassB){
			System.out.println("Object b is a ClassB Object!");
		} else {
			System.out.println("Object b is not a ClassB Object!");
		}
	}
}
