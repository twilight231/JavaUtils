package com.zstax.util.常用类.thread;

/**
 * Daemon(�ػ�)�߳� 
 * Daemon�߳�����һ���߳�֮���ǣ� 
 * ֻ��������е��û��̣߳���Daimon�̣߳�ȫ��������Daemon�߳̾ͻ���������,����Ҳ�������finally�����䡣
 * daemon�߳��������������̶߳���daemon��
 */
public class Daemon {

	static class MainThread extends Thread {

		public void run() {
			System.out.println("MainThread is daemon? " + this.isDaemon());
			System.out.println("MainThread begin!");
			//�������߳�
			Thread sub1 = new SubThread();
			//sub1�߳�Ϊ�ػ��߳�
			sub1.setDaemon(true);
			sub1.start();
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("MainThread" + " finally");
			}
			System.out.println("MainThread end!");
		}
	}

	static class SubThread extends Thread {

		public void run() {
			System.out.println("SubThread is daemon? " + this.isDaemon());
			System.out.println("SubThread begin!");
			int i = 0;
			try {
				while (i < 10) {
					System.out.println("SubThread  " + i++);
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("SubThread finally");
			}
			System.out.println("SubThread end!");
		}
	}

	public static void main(String[] args) {
		System.out.println("Main begin!");
		//Ĭ�������mainThread����ͨ�߳�
		Thread mainThread = new MainThread();
		//����mainThread�߳�
		mainThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("Main end!");
	}
}