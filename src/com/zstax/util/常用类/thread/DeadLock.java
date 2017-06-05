package com.zstax.util.常用类.thread;

/**
 * һ���߳�����������
 */
public class DeadLock {
	public static void main(String[] args) {
		// 2����Դ
		final Object resource1 = "resource1";
		final Object resource2 = "resource2";
		// ��һ���̣߳�����ռ��resource1���ٳ�����ռ��resource2
		Thread t1 = new Thread() {
			public void run() {
				//����ռ����resource1
				synchronized (resource1) {
					//�ɹ�ռ��resource1
					System.out.println("Thread 1: locked resource 1");
					//����һ��ʱ��
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					// ������ռ��resource2���������ռ�У����̻߳�һֱ�ȴ�
					synchronized (resource2) {
						System.out.println("Thread 1: locked resource 2");
					}
				}
			}
		};

		// �ڶ����̣߳�����ռ��resource2����ռ��resource1
		Thread t2 = new Thread() {
			public void run() {
				// ������ռ��resource2
				synchronized (resource2) {
					// �ɹ�ռ��resource2
					System.out.println("Thread 2: locked resource 2");
					// ����һ��ʱ��
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					// ����ռ��resource1���������ռ�У����̻߳�һֱ�ȴ�
					synchronized (resource1) {
						System.out.println("Thread 2: locked resource 1");
					}
				}
			}
		};
		// ���������߳�
		t1.start();
		t2.start();
	}
}
