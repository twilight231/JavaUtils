package com.zstax.util.常用类.oo.initorder;

public class Child extends Parent {
	{
		System.out.println("Child�ĳ�ʼ����");
		int bx = 100;
	}
	static {
		System.out.println("Child�ľ�̬��ʼ����");
	}
	public Child() {
		super();
		System.out.println("Child�Ĺ��췽��������");
	}
	public static void dispB(){
		System.out.println("Child��dispB()������");
	}
	/**
	 * ��Java�ڽ�����������ʱ������ö����finalize����
	 */
	protected void finalize() {
		System.out.println("Child�����ٷ���������");
		super.finalize();
	}
}