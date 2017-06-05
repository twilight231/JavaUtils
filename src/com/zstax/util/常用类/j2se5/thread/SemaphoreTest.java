package com.zstax.util.常用类.j2se5.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java 5.0���¼���4��Э���̼߳���̵�ͬ��װ�ã����Ƿֱ��ǣ�
 * Semaphore, CountDownLatch, CyclicBarrier��Exchanger.
 * ������Ҫ����Semaphore��
 * Semaphore����������һ����Դ�صĹ��ߣ����Կ����Ǹ�ͨ��֤��
 * �߳�Ҫ�����Դ���õ���Դ�������õ�ͨ��֤��
 * ����߳���ʱ�ò���ͨ��֤���߳̾ͻᱻ��Ͻ���ȴ�״̬��
 */
public class SemaphoreTest {
	/**
	 * ģ����Դ�ص���
	 * ֻΪ�ط���2��ͨ��֤����ͬʱֻ����2���̻߳�ó��е���Դ��
	 */
	public static class Pool {
		// ������Դ���е���Դ
		ArrayList<String> pool = null;
		// ͨ��֤
		Semaphore pass = null;
		Lock lock = new ReentrantLock();
		public Pool(int size) {
			// ��ʼ����Դ��
			pool = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				pool.add("Resource " + i);
			}
			// ����2��ͨ��֤
			pass = new Semaphore(2);
		}

		public String get() throws InterruptedException {
			// ��ȡͨ��֤,ֻ�еõ�ͨ��֤����ܵõ���Դ
			System.out.println("Try to get a pass...");
			pass.acquire();
			System.out.println("Got a pass");
			return getResource();
		}

		public void put(String resource) {
			// �黹ͨ��֤�����黹��Դ
			System.out.println("Released a pass");
			pass.release();
			releaseResource(resource);
		}

		private String getResource() {
			lock.lock();
			String result = pool.remove(0);
			System.out.println("��Դ " + result + " ��ȡ��");
			lock.unlock();
			return result;
		}

		private void releaseResource(String resource) {
			lock.lock();
			System.out.println("��Դ " + resource + " ���黹");
			pool.add(resource);
			lock.unlock();
		} 
	}
	
	public static void testPool() {
		// ׼��10����Դ����Դ��
		final Pool aPool = new Pool(10);
		Runnable worker = new Runnable() {
			public void run() {
				String resource = null;
				try {
					//ȡ��resource
					resource = aPool.get();
					//��resource������
					System.out.println("I am working on " + resource);
					Thread.sleep(500);
					System.out.println("I finished on " + resource);
				} catch (InterruptedException ex) {
				}
				//�黹resource
				aPool.put(resource);
			}
		};
		// ����5������
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			service.submit(worker);
		}
		service.shutdown();
	} 
	
	public static void main(String[] args) {
		SemaphoreTest.testPool();
	}
}