package com.zstax.util.常用类.oo.singleton;

public class SingletonB {
	//˽������
	private static int id = 1;
	//SingletonB��Ψһʵ��
	private static SingletonB instance = null;
	
	//�����캯��˽�У���ֹ��繹��SingletonBʵ��
	private SingletonB() {
	}
	//��ȡSingletonB��Ψһʵ����ͬ����synchronized�ؼ��ֱ�֤ĳһʱ��ֻ��һ���̵߳��ô˷�����
	public static synchronized SingletonB getInstance() {
		//���instanceΪ�գ��㴴��һ���µ�SingletonBʵ�������򣬷������е�ʵ��
		if (instance == null) {
			instance = new SingletonB();
		}
		return instance;
	}
	public synchronized int getId() {
		return id;
	}
	public synchronized void nextID() {
		id++;
	}
}