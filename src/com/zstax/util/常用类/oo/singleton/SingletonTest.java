package com.zstax.util.常用类.oo.singleton;

/*
 * ģʽ���ƣ�����ģʽ
 * ģʽ������ֻ�ܴ��������һ��ʵ��
 * ģʽ��;���ṩһ��ȫ�ֹ�����ʵ��
 **/
public class SingletonTest {

	public static void main(String[] args) {

		SingletonA a1 = SingletonA.getInstance();
		SingletonA a2 = SingletonA.getInstance();
		System.out.println("��SingletonAʵ�ֵ���ģʽ");
		System.out.println("����nextID����ǰ��");
		System.out.println("a1.id=" + a1.getId());
		System.out.println("a2.id=" + a2.getId());
		a1.nextID();
		System.out.println("����nextID������");
		System.out.println("a1.id=" + a1.getId());
		System.out.println("a2.id=" + a2.getId());
		//SingletonA��SingletonB������ǰ�������౻���ص�ʱ��ʹ�����ʵ����
		//���������ڵ���getInstance����ʱ�Ŵ���ʵ����
		SingletonB b1 = SingletonB.getInstance();
		SingletonB b2 = SingletonB.getInstance();
		System.out.println("��SingletonBʵ�ֵ���ģʽ");
		System.out.println("����nextID����ǰ��");
		System.out.println("b1.id=" + b1.getId());
		System.out.println("b2.id=" + b2.getId());
		b1.nextID();
		System.out.println("����nextID������");
		System.out.println("b1.id=" + b1.getId());
		System.out.println("b2.id=" + b2.getId());
	}
}