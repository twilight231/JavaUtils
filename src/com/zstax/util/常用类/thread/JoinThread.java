package com.zstax.util.常用类.thread;

/**
 * �̵߳Ľ�ϡ�
 * ��һ���߳���Ҫ�ȴ���һ���߳̽���ʱ�������̵߳Ľ�ϡ�
 */
public class JoinThread {
	/**	�Զ����߳��� */
	static class ThreadA extends Thread{
		//�̵߳�ID
		private int ID = 0;
		//�߳�����ʱѭ���Ĵ���
		private int whileTimes = 0;
		public ThreadA(int id, int times){
			this.ID = id;
			this.whileTimes = times;
		}
		public void run(){
			System.out.println("ThreadA" + this.ID + " begin!");
			int i=0; 
			try {
				//����ѭ��whileTimes��
				while (i < this.whileTimes){
					System.out.println("ThreadA-" + this.ID + ": " + i++);
					//sleep��������ǰ�߳����ߡ�
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
			}

			System.out.println("ThreadA" + this.ID + " end!");
		}
	}
	public static void main(String[] args) {
		//�½�4���̶߳���
		Thread thread1 = new ThreadA(1, 3);
		Thread thread2 = new ThreadA(2, 2);
		Thread thread3 = new ThreadA(3, 2);
		Thread thread4 = new ThreadA(4, 4);
		//���������߳�
		System.out.println("Main method begin. To start 4 threads!");
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		//�ȴ������߳����н���
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//��ʱ�����̶߳����н���
		System.out.println("Main method end! All 4 threads are ended");
	}
}
