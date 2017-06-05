package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Callable �� Future�ӿ�
 * Callable��������Runnable�Ľӿڣ�ʵ��Callable�ӿڵ����ʵ��Runnable���඼�ǿɱ������߳�ִ�е�����
 * Callable��Runnable�м��㲻ͬ��
 * ��1��Callable�涨�ķ�����call()����Runnable�涨�ķ�����run().
 * ��2��Callable������ִ�к�ɷ���ֵ����Runnable�������ǲ��ܷ���ֵ�ġ�
 * ��3��call()�������׳��쳣����run()�����ǲ����׳��쳣�ġ�
 * ��4������Callable������õ�һ��Future����
 * Future ��ʾ�첽����Ľ�������ṩ�˼������Ƿ���ɵķ������Եȴ��������ɣ�����������Ľ����
 * ͨ��Future������˽�����ִ���������ȡ�������ִ�У����ɻ�ȡ����ִ�еĽ����
 */
public class CallableAndFuture {

	/**
	 * �Զ���һ�������࣬ʵ��Callable�ӿ�
	 */
	public static class MyCallableClass implements Callable{
		// ��־λ
		private int flag = 0;
		public MyCallableClass(int flag){
			this.flag = flag;
		}
		public String call() throws Exception{
			if (this.flag == 0){
				// ���flag��ֵΪ0������������
				return "flag = 0";
			} 
			if (this.flag == 1){
				// ���flag��ֵΪ1����һ������ѭ��
				try {
					while (true) {
						System.out.println("looping....");
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					System.out.println("Interrupted");
				}
				return "false";
			} else {
				// falg��Ϊ0����1�����׳��쳣
				throw new Exception("Bad flag value!");
			}
		}
	}
	
	public static void main(String[] args) {
		// ����3��Callable���͵�����
		MyCallableClass task1 = new MyCallableClass(0);
		MyCallableClass task2 = new MyCallableClass(1);
		MyCallableClass task3 = new MyCallableClass(2);
		
		// ����һ��ִ������ķ���
		ExecutorService es = Executors.newFixedThreadPool(3);
        try {
    		// �ύ��ִ��������������ʱ������һ��Future����
    		// �����õ�����ִ�еĽ���������쳣�ɶ����Future������в���
    		Future future1 = es.submit(task1);
        	// ��õ�һ������Ľ�����������get��������ǰ�̻߳�ȴ�����ִ����Ϻ������ִ��
        	System.out.println("task1: " + future1.get());
        	
            Future future2 = es.submit(task2);
        	// �ȴ�5�����ֹͣ�ڶ���������Ϊ�ڶ���������е�������ѭ��
        	Thread.sleep(5000);
        	System.out.println("task2 cancel: " + future2.cancel(true));
        	
        	// ��ȡ������������������Ϊִ�е���������������쳣
        	// �����������佫�����쳣���׳�
            Future future3 = es.submit(task3);
        	System.out.println("task3: " + future3.get());
        } catch (Exception e){
        	System.out.println(e.toString());
        }
        // ֹͣ����ִ�з���
        es.shutdownNow();
	}
}
