package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * �µ�����ִ�мܹ���
 * ��Java 5.0֮ǰ����һ��������ͨ������Thread���start()������ʵ�ֵģ�
 * ��������ڽ���ִ����ͬʱ���еģ��������������ִ�н��е��ȣ�
 * ���ǿ���ͬʱִ�е��߳���������Ҫ�����д��������ɡ�
 * 5.0���ṩ��һ���µ�����ִ�мܹ�ʹ��������ɵص��ȺͿ��������ִ�У�
 * ���ҿ��Խ���һ���������ݿ����ӳص��̳߳���ִ������
 * ����ܹ���Ҫ�������ӿں�����Ӧ�ľ�������ɡ�
 * �������ӿ���Executor, ExecutorService��ScheduledExecutorService��
 * ��1��Executor�ӿڣ�������ִ��Runnable����ģ���ֻ����һ��������
 * execute(Runnable command)��ִ��Ruannable���͵�����
 * ��2��ExecutorService���̳���Executor�ķ��������ṩ��ִ��Callable�������ֹ����ִ�еķ���
 * �䶨��ķ�����Ҫ�У�
 * submit(task)���������ύCallable��Runnable���񣬲����ش���������Future���� 
 * invokeAll(collection of tasks)�����������񼯺ϣ�������һ��������Щ�����Future���󼯺� 
 * shutdown()����������ύ�������رշ��񣬲��ٽ��������� 
 * shutdownNow()��ֹͣ��������ִ�е����񲢹رշ��� 
 * isTerminated()�������Ƿ���������ִ������ˡ� 
 * isShutdown()�������Ƿ��ExecutorService�ѱ��ر�
 * ��3��ScheduledExecutorService���̳�ExecutorService���ṩ�˰�ʱ�䰲��ִ������Ĺ��ܡ�
 * schedule(task, initDelay): �������ύ��Callable��Runnable������initDelayָ����ʱ���ִ�С�
 * scheduleAtFixedRate()���������ύ��Runnable����ָ���ļ���ظ�ִ�� 
 * scheduleWithFixedDelay()���������ύ��Runnable������ÿ��ִ����󣬵ȴ�delay��ָ����ʱ����ظ�ִ�С�
 * 
 * ͨ��Executors������ø��ַ������
 * callable(Runnable task): ��Runnable������ת����Callable������ 
 * newSingleThreadExecutor: ����һ��ExecutorService�����������ֻ��һ���߳̿�����ִ���������������һ�������񽫰��Ⱥ�˳��ִ�С� 
 * newCachedThreadPool(): ����һ��ExecutorService��������������һ���̳߳أ��̳߳صĴ�С�������Ҫ�������߳�ִ��������󷵻��̳߳أ���ִ����һ������ʹ�á�
 * newFixedThreadPool(int poolSize)������һ��ExecutorService��������������һ����СΪpoolSize���̳߳أ���������������poolSize������ᱻ����һ��queue��˳��ִ�С� 
 * newSingleThreadScheduledExecutor������һ��ScheduledExecutorService�������������̳߳ش�СΪ1�����������һ�������񽫰��Ⱥ�˳��ִ�С� 
 * newScheduledThreadPool(int poolSize): ����һ��ScheduledExecutorService�������������̳߳ش�СΪpoolSize����������������poolSize���������һ��queue��ȴ�ִ�� 
 */
public class ExecuteArch {
	
	/**
	 * ���߳����һ���ַ���
	 */
	public static class MyThread implements Runnable {
		public void run() {
			System.out.println("Task repeating. " + System.currentTimeMillis());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Task interrupted. "
						+ System.currentTimeMillis());
			}
		}
	}

	/**
	 * ��Callable������һ������
	 */
	public static class MyCallable implements Callable {
		private Future future;

		public MyCallable(Future future) {
			this.future = future;
		}

		public String call() {
			System.out.println("To cancell Task..."
					+ +System.currentTimeMillis());
			this.future.cancel(true);
			return "Task cancelled!";
		}
	}

	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		// ����һ��ExecutorService��������������һ���̳߳أ��̳߳صĴ�С�������Ҫ������
		// �߳�ִ��������󷵻��̳߳أ���ִ����һ������ʹ�á�
		ExecutorService cachedService = Executors.newCachedThreadPool();
		Future myThreadFuture = cachedService.submit(new MyThread());
		Future myCallableFuture = cachedService.submit(new MyCallable(
				myThreadFuture));
		System.out.println(myCallableFuture.get());
		System.out.println("-----------------");

		// ��Runnable����ת����Callable����
		Callable myThreadCallable = Executors.callable(new MyThread());
		Future myThreadCallableFuture = cachedService.submit(myThreadCallable);
		// ����Runnable����ת����Callable�����Ҳû�з���ֵ
		System.out.println(myThreadCallableFuture.get());
		cachedService.shutdownNow();
		System.out.println("-----------------");

		// ����һ��ExecutorService��������������һ����СΪpoolSize���̳߳أ�
		// ��������������poolSize������ᱻ����һ��queue��˳��ִ��
		ExecutorService fixedService = Executors.newFixedThreadPool(2);
		fixedService.submit(new MyThread());
		fixedService.submit(new MyThread());
		// �����̳߳ش�СΪ2�����Ժ�����������ȴ�ǰ�������ִ�������ܱ�ִ�С�
		myThreadFuture = fixedService.submit(new MyThread());
		myCallableFuture = fixedService.submit(new MyCallable(myThreadFuture));
		System.out.println(myCallableFuture.get());
		fixedService.shutdownNow();
		System.out.println("-----------------");

		// ����һ��ScheduledExecutorService�������������̳߳ش�СΪpoolSize��
		// ��������������poolSize���������һ��queue��ȴ�ִ��
		ScheduledExecutorService fixedScheduledService = Executors
				.newScheduledThreadPool(2);
		// �½�����1
		MyThread task1 = new MyThread();
		// ʹ������ִ�з�������ִ������1�����Ҵ˺�ÿ��2��ִ��һ������1��
		myThreadFuture = fixedScheduledService.scheduleAtFixedRate(task1, 0, 2,
				TimeUnit.SECONDS);
		// �½�����2
		MyCallable task2 = new MyCallable(myThreadFuture);
		// ʹ������ִ�з���ȴ�5���ִ������2��ִ������Ὣ����1�رա�
		myCallableFuture = fixedScheduledService.schedule(task2, 5,
				TimeUnit.SECONDS);
		System.out.println(myCallableFuture.get());
		fixedScheduledService.shutdownNow();
	}
}
