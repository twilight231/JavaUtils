package com.zstax.util.常用类.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * ͨ�����䶯̬������ľ�̬������ʵ��������
 */
public class CallMethod {

	public static void main(String[] args) throws Exception {
		// ��ȡTestClass��Class����
		Class testClass = Class.forName(TestClass.class.getName());

		/** *** ����ʵ�� **** */
		// ��1��ʹ��Class�����newInstance��������һ��ʵ�������ַ�����Ĭ�Ϲ��췽����������
		TestClass objectA = (TestClass) testClass.newInstance();
		System.out.println("Class��newInstance() ��������Ĭ��TestClassʵ��: "
				+ objectA.toString());
		// ��2��ʹ�ù��췽������ʵ������Ϳ���ʹ�ô������Ĺ��췽������ʵ����
		Constructor[] cons = testClass.getDeclaredConstructors();
		System.out.println("testClass�� " + cons.length + " �����췽��");
		Constructor con = null;
		for (int i = 0; i < cons.length; i++) {
			con = cons[i];
			// Ĭ�Ϲ��캯��
			if (con.getParameterTypes().length == 0) {
				// ����Constructor��newInstance��������ʵ��
				objectA = (TestClass) con.newInstance(null);
				System.out
						.println("Constructor �� newInstance() ��������Ĭ��TestClassʵ��: "
								+ objectA.toString());
			} else {
				// �������Ĺ��캯��
				objectA = (TestClass) con.newInstance(new Object[] {
						new Integer(55), new Integer(88) });
				System.out
						.println("Constructor �� newInstance() ����������������TestClassʵ��: "
								+ objectA.toString());
			}
		}

		/** *** ��ȡ���� *** */
		// ��ȡ���з���
		Method[] methods = testClass.getMethods();
		// ��ȡĳ���ض����޲����ķ���
		Method saddMethod1 = testClass.getMethod("sadd", null);
		Method addMethod1 = testClass.getMethod("add", null);
		// ��ȡĳ���ض����в����ķ���
		Method saddMethod2 = testClass.getMethod("sadd", new Class[] {
				int.class, int.class });
		Method addMethod2 = testClass.getMethod("add", new Class[] { int.class,
				int.class });

		/** *** ���þ�̬���� **** */
		// ���ò��������ľ�̬����
		int result = ((Integer) saddMethod1.invoke(null, null)).intValue();
		System.out.println("���ò��������ľ�̬����sadd: " + result);
		// ���ô������ľ�̬����
		result = ((Integer) saddMethod2.invoke(null, new Object[] {
				new Integer(30), new Integer(70) })).intValue();
		System.out.println("���ô�����30, 70�ľ�̬����sadd: " + result);

		/** *** ����ʵ������ **** */
		objectA = (TestClass) testClass.newInstance();
		// ���ò���������ʵ������
		result = ((Integer) addMethod1.invoke(objectA, null)).intValue();
		System.out.println("���ò���������ʵ������add: " + result);
		// ���ô�������ʵ������
		result = ((Integer) addMethod2.invoke(objectA, new Object[] {
				new Integer(130), new Integer(170) })).intValue();
		System.out.println("���ô�����130, 170��ʵ������add: " + result);

		// ���ܷ���˽�з���
//		Method sub = testClass.getMethod("sub", null);
//		System.out.println(sub.invoke(objectA, null));
	}

	// ������
	static class TestClass {
		// ������̬����
		static int sa = 100;
		static int sb = 50;
		// ����ʵ������
		int a;
		int b;
		// Ĭ�Ϲ��췽��
		public TestClass() {
			this.a = 5;
			this.b = 10;
		}
		// �������Ĺ��췽��
		public TestClass(int a, int b) {
			this.a = a;
			this.b = b;
		}

		// ��̬������ʵ��add����
		public static int sadd() {
			return sa + sb;
		}
		public static int sadd(int a, int b) {
			return a + b;
		}
		// ʵ��������ʵ��add����
		public int add() {
			return this.a + this.b;
		}
		public int add(int a, int b) {
			return a + b;
		}
		public String toString() {
			return "a = " + this.a + "; b = " + this.b;
		}
		// ˽�з���
		private int sub() {
			return this.a - this.b;
		}
	}
}
