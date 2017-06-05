package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch�Ǹ�������������һ����ʼ����
 * �ȴ�������������̱߳���ȵ���������������ʱ�ſɼ�����
 */
public class CountDownLatchTest {

	/**
	 * ��ʼ��������߳�
	 */
	public static class ComponentThread implements Runnable {
		// ������
		CountDownLatch latch;
		// ���ID
		int ID;

		// ���췽��
		public ComponentThread(CountDownLatch latch, int ID) {
			this.latch = latch;
			this.ID = ID;
		}

		public void run() {
			// ��ʼ�����
			System.out.println("Initializing component " + ID);
			try {
				Thread.sleep(500 * ID);
			} catch (InterruptedException e) {
			}
			System.out.println("Component " + ID + " initialized!");
			//����������һ
			latch.countDown();
		}
	}

	/**
	 * ����������
	 */
	public static void startServer() throws Exception {
		System.out.println("Server is starting.");
		//��ʼ��һ����ʼֵΪ3��CountDownLatch
		CountDownLatch latch = new CountDownLatch(3);
		//��3���̷ֱ߳�ȥ����3�����
		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(new ComponentThread(latch, 1));
		service.submit(new ComponentThread(latch, 2));
		service.submit(new ComponentThread(latch, 3));
		service.shutdown();

		//�ȴ�3������ĳ�ʼ�����������
		latch.await();

		//�������������������ʱ��Server�Ϳɼ�����
		System.out.println("Server is up!");
	}

	public static void main(String[] args) throws Exception {
		CountDownLatchTest.startServer();
	}
}