package com.zstax.util.常用类.oo.initorder;

public class Parent {
	private int ix = 50;

	private static int iz = getNext(30);
	{
		System.out.println("Parent�ĳ�ʼ����");
		int x = 100;
		int y = getNext(100);
	}
	static {
		System.out.println("Parent�ľ�̬��ʼ����");
		int sx = 100;
		int sy = getNext(100);
	}
	public Parent() {
		System.out.println("Parent�Ĺ��췽��������");
	}
	public void display() {
		System.out.println("Parent��display����������");
		System.out.print("ix=" + this.ix);
		System.out.println("; iz=" + iz);
		dispA();
	}
	public static void dispA() {
		System.out.println("Parent��dispA()������");
	}
	public static int getNext(int base) {
		System.out.println("Parent��getNext(int base)������");
		return ++base;
	}
	/**
	 * ��Java�ڽ�����������ʱ������ö����finalize����
	 */
	protected void finalize() {
		System.out.println("Parent�����ٷ���������");
	}
}