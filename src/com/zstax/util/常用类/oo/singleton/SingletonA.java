package com.zstax.util.常用类.oo.singleton;

public class SingletonA {
	//˽������
	private static int id = 1;
	//SingletonA��Ψһʵ��
	private static SingletonA instance = new SingletonA();
	
	//�����캯��˽�У���ֹ��繹��SingletonAʵ��
	private SingletonA() {
	}
	/**
	 * ��ȡSingletonA��ʵ��
	 */
	public static SingletonA getInstance() {
		return instance;
	}
	/**
	 * ��ȡʵ����id,synchronized�ؼ��ֱ�ʾ�÷������߳�ͬ���ģ�
	 * ����һʱ�����ֻ����һ���߳̽���÷���
	 * @return
	 */
	public synchronized int getId() {
		return id;
	}
	/**
	 * ��ʵ����id��1
	 */
	public synchronized void nextID() {
		id++;
	}

}