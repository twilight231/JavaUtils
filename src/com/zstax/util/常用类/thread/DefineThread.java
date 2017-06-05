package com.zstax.util.常用类.thread;

import java.util.Date;

public class DefineThread {
	/**
	 * ͨ���̳�java.lang.Thread�ඨ���߳�
	 */
	class ThreadA extends Thread{
		/**	�̱߳����е�ʱ��	*/
		private Date runDate;
		/**
		 * ���̱߳�����ʱ���ô˷���
		 */
		public void run(){
			System.out.println("ThreadA begin,");
			this.runDate = new Date();
			System.out.println("ThreadA end.");
		}
	}
	
	/**
	 * ͨ��ʵ��java.lang.Runnable�ӿڶ����߳�
	 */
	class ThreadB implements Runnable{
		/**	�̱߳����е�ʱ��	*/
		private Date runDate;
		public void run(){
			System.out.println("ThreadB begin,");
			this.runDate = new Date();
			System.out.println("ThreadB end.");
		}
	}
	/**
	 * ����һ��ThreadA�߳�
	 */
	public void startA(){
		Thread threadA = new ThreadA();
		//����Thread��start���������߳�
		threadA.start();
	}
	/**
	 * ����һ��ThreadB�߳�
	 */
	public void startB(){
		Runnable tb = new ThreadB();
		//��Runnable�������߳�
		Thread threadB = new Thread(tb);
		threadB.start();
	}
	
	public static void main(String[] args) {
		DefineThread test = new DefineThread();
		//�̵߳����о��в�ȷ���ԣ����������̲߳�һ�������У�ȡ�����������
		test.startA();
		test.startB();
	}
}
