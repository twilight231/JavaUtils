package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ��ʱ���߳�ȡ��lock����Ҫ��һ�������²�����ĳЩ����������˵�����Producer��Consumer���⡣
 * ��Java 5.0��ǰ�����ֹ�������Object���wait(), notify()��notifyAll()�ȷ���ʵ�ֵģ�
 * ��5.0���棬��Щ���ܼ��е���Condition����ӿ���ʵ�֡�
 */
public class ConditionTest {

	/**
	 * ���ӳ�������Ϊ�˼����⣬���������ֻ����һ��ƻ����
	 * Consumer��������������ƻ����ʱ����ܳ�ƻ����������������ʱ���������ӵ�������
	 * �ȵ�Producer�����������ƻ������ȥ�����ԡ���Producer����ȵ����ӿ��˲��������ƻ����
	 * ������Ҳ��Ҫ��ʱ������Consumer��ƻ�����˲������������ƻ����
	 */
	public static class Basket {
		// ��
		Lock lock = new ReentrantLock();
		//	����������Condition����
		Condition produced = lock.newCondition();
		Condition consumed = lock.newCondition();
		// �����е�ƻ���������Ϊ1
		int num = 0;

		/**
		 * ����ƻ�������������
		 * @throws InterruptedException
		 */
		public void produce() throws InterruptedException {
			// �����
			lock.lock();
			System.out.println("Producer get a lock...");
			try {
				// �ж��Ƿ�������������
				while (num == 1) {
					// �����ƻ������������������������˯��
					// �ȴ�����������
					System.out.println("Producer sleep...");
					consumed.await(); 
					System.out.println("Producer awaked...");
				}
				/*����ƻ��*/
				Thread.sleep(500);
				System.out.println("Producer produced an Apple.");
				num = 1;
				// ֪ͨ�ȴ�produced Condition���߳�
				produced.signal();
			} finally {
				lock.unlock();
			}
		}
		/**
		 * ����ƻ������������ȡ
		 * @throws InterruptedException
		 */
		public void consume() throws InterruptedException {
			// �����
			lock.lock();
			System.out.println("Consumer get a lock...");
			try {
				// �ж��Ƿ�������������
				while (num == 0) {
					// ���û��ƻ�����޷����ѣ��������������˯��
					// �ȴ�����������ƻ��
					System.out.println("Consumer sleep...");
					produced.await();  
					System.out.println("Consumer awaked...");
				}
				/*��ƻ��*/
				Thread.sleep(500);
				System.out.println("Consumer consumed an Apple.");
				num = 0;
				// ���źŻ���ĳ���ȴ�consumed Condition���߳�
				consumed.signal();
			} finally {
				lock.unlock();
			}
		}
	}
	/**
	 * ����Basket����
	 */
	public static void testBasket() throws Exception {
		final Basket basket = new Basket();
		//	����һ��producer
		Runnable producer = new Runnable() {
			public void run() {
				try {
					basket.produce();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		};

		// ����һ��consumer
		Runnable consumer = new Runnable() {
			public void run() {
				try {
					basket.consume();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		};

		//	������3��consumer��producer
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++){
			service.submit(producer);
		}
		for (int i = 0; i < 3; i++){
			service.submit(consumer);
		}
		service.shutdown();
	}      

	public static void main(String[] args) throws Exception {
		ConditionTest.testBasket();
	}
}