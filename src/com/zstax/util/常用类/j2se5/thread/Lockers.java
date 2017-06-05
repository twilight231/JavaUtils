package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Lockers
 * �ڶ��̱߳������һ����Ҫ�ĸ��������������һ����Դ�Ƕ���̹߳���ģ�Ϊ�˱�֤���ݵ������ԣ�
 * �ڽ��������Բ���ʱ��Ҫ��������Դ�������������Ա�֤���������Բ���ʱֻ��һ���߳��ܶ���Դ���в�����
 * �Ӷ���֤���ݵ������ԡ���5.0��ǰ�������Ĺ�������Synchronized�ؼ�����ʵ�ֵġ�
 */
public class Lockers {
	
	/**
	 * ����Lock��ʹ�á��ڷ�����ʹ��Lock�����Ա���ʹ��Synchronized�ؼ��֡�
	 */
	public static class LockTest {

		Lock lock = new ReentrantLock();// ��
		double value = 0d; // ֵ
		int addtimes = 0;

		/**
		 * ����value��ֵ���÷����Ĳ�����Ϊ2���������໥����������ʵ����һ��������
		 * ���Ը÷�������ͬ������ǰ���������ڷ���������ʹ��Synchronized�ؼ��֡�
		 */
		public void addValue(double v) {
			lock.lock();// ȡ����
			System.out.println("LockTest to addValue: " + v + "   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			this.value += v;
			this.addtimes++;
			lock.unlock();// �ͷ���
		}

		public double getValue() {
			return this.value;
		}
	}
	public static void testLockTest() throws Exception{
		final LockTest lockTest = new LockTest();
		// �½�����1������lockTest��addValue����
		Runnable task1 = new Runnable(){
			public void run(){
				lockTest.addValue(55.55);
			}
		};
		// �½�����2������lockTest��getValue����
		Runnable task2 = new Runnable(){
			public void run(){
				System.out.println("value: " + lockTest.getValue());
			}
		};
		// �½�����ִ�з���
		ExecutorService cachedService = Executors.newCachedThreadPool();
		Future future = null;
		// ͬʱִ������1���Σ�����addValue����ʹ���������ƣ����ԣ�ʵ���ϻ�˳��ִ��
		for (int i=0; i<3; i++){
			future = cachedService.submit(task1);
		}
		// �ȴ����һ������1��ִ����
		future.get();
		// ��ִ������2��������
		future = cachedService.submit(task2);
		// �ȴ�����2ִ����󣬹ر�����ִ�з���
		future.get();
		cachedService.shutdownNow();
	}
	
	/**
	 * ReadWriteLock��������Lock��һ���Ƕ���Lock��һ����д��Lock��
	 * ����߳̿�ͬʱ�õ�����Lock����ֻ��һ���߳��ܵõ�д��Lock��
	 * ����д��Lock���������κ��̶߳����ܵõ�Lock��ReadWriteLock�ṩ�ķ����У�
	 * readLock(): ����һ������lock 
	 * writeLock(): ����һ��д��lock, ��lock�������ġ�
	 * ReadWriteLockTest���ʺϴ��������ļ��Ķ�д������
	 * ����ʱ�����ͬʱ����������д��д��ʱ��Ȳ���ͬʱдҲ���ܶ���
	 */
	public static class ReadWriteLockTest{
		// ��
		ReadWriteLock lock = new ReentrantReadWriteLock();
		// ֵ
		double value = 0d;
		int addtimes = 0;
		
		/**
		 * ����value��ֵ�����������߳�ͬʱ����÷���
		 */
		public void addValue(double v) {
			// �õ�writeLock������
			Lock writeLock = lock.writeLock();
			writeLock.lock();
			System.out.println("ReadWriteLockTest to addValue: " + v + "   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				// ��д�Ĺ���
				this.value += v;
				this.addtimes++;
			} finally {
				// �ͷ�writeLock��
				writeLock.unlock();
			}
		}
		/**
		 * �����Ϣ�������߳��ڵ���addValue����ʱ��getInfo�õ�����Ϣ�����ǲ���ȷ�ġ�
		 * ���ԣ�Ҳ���뱣֤�÷����ڱ�����ʱ��û�з����ڵ���addValue������
		 */
		public String getInfo() {
			// �õ�readLock������
			Lock readLock = lock.readLock();
			readLock.lock();
			System.out.println("ReadWriteLockTest to getInfo   "
					+ System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			try {
				// �����Ĺ���
				return this.value + " : " + this.addtimes;
			} finally {
				// �ͷ�readLock
				readLock.unlock();
			}
		}
	}
	
	public static void testReadWriteLockTest() throws Exception{
		final ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
		// �½�����1������lockTest��addValue����
		Runnable task_1 = new Runnable(){
			public void run(){
				readWriteLockTest.addValue(55.55);
			}
		};
		// �½�����2������lockTest��getValue����
		Runnable task_2 = new Runnable(){
			public void run(){
				System.out.println("info: " + readWriteLockTest.getInfo());
			}
		};
		// �½�����ִ�з���
		ExecutorService cachedService_1 = Executors.newCachedThreadPool();
		Future future_1 = null;
		// ͬʱִ��5����������ǰ2��������task_1��������������task_2
		for (int i=0; i<2; i++){
			future_1 = cachedService_1.submit(task_1);
		}
		for (int i=0; i<2; i++){
			future_1 = cachedService_1.submit(task_2);
		}
		// ���һ��������task_1
		future_1 = cachedService_1.submit(task_1);
		// ��5�������ִ��˳��Ӧ���ǣ�
		// ��һ��task_1��ִ�У��ڶ���task_1��ִ�У�������Ϊ����ͬʱд�����Ա���ȡ�
		// Ȼ��2��task_2ͬʱִ�У�������Ϊ��д��ʱ�򣬾Ͳ��ܶ������Զ��ȴ�д������
		// ����Ϊ����ͬʱ������������ͬʱִ��
		// ���һ��task_1��ִ�С�������Ϊ�ڶ���ʱ��Ҳ����д�����Ա���ȴ��������󣬲���д��
		
		// �ȴ����һ��task_2��ִ����
		future_1.get();
		cachedService_1.shutdownNow();
	}

	public static void main(String[] args) throws Exception{
		Lockers.testLockTest();
		System.out.println("---------------------");
		Lockers.testReadWriteLockTest();
	}
}
