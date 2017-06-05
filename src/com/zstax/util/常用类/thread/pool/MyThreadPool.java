package com.zstax.util.常用类.thread.pool;

import java.util.LinkedList;

/**
 * �̳߳أ��̳�ThreadGroup��
 * ThreadGroup���ڴ���һ���̵߳��࣬����һ����״�ṹ,�����²�ڵ㻹������ThreadGroup����
 */
public class MyThreadPool extends ThreadGroup {

	/**	��־�̳߳��Ƿ���	*/
	private boolean isAlive;
	/**	�̳߳��е��������	*/
	private LinkedList taskQueue;
	/**	�̳߳��е��߳�ID	*/
	private int threadID;
	/**	�̳߳�ID	*/
	private static int threadPoolID;

	/**
	 *  �����µ��̳߳أ�numThreads�ǳ��е��߳���
	 */
	public MyThreadPool(int numThreads) {
		super("ThreadPool-" + (threadPoolID++));
		//����Ϊ���̳߳��ǵ�daemon����Ϊtrue��
		//��ʾ�����̳߳��������̶߳�������ʱ�����̳߳ػ��Զ�������
		super.setDaemon(true);
		this.isAlive = true;
		//�½�һ���������
		this.taskQueue = new LinkedList();
		//����numThreads�������߳�
		for (int i = 0; i < numThreads; i++) {
			new PooledThread().start();
		}
	}
	/**
	 * ���������
	 */
	public synchronized void performTask(Task task) {
		if (!this.isAlive) {
			//	�̱߳������׳�IllegalStateException�쳣
			throw new IllegalStateException();
		}
		if (task != null) {
			//������ŵ�������е�β��
			this.taskQueue.add(task);
			//֪ͨ�����߳�ȡ����
			notify();
		}

	}

	/**
	 * ��ȡ����
	 */
	protected synchronized Task getTask() throws InterruptedException {
		//��������б�Ϊ�գ������̳߳�û�б��رգ�������ȴ�����
		while (this.taskQueue.size() == 0) {
			if (!this.isAlive) {
				return null;
			}
			wait();
		}
		//ȡ�����б�ĵ�һ������
		return (Task) this.taskQueue.removeFirst();
	}

	/**
	 * �ر��̳߳أ������߳�ֹͣ������ִ������
	 */
	public synchronized void close() {
		if (isAlive) {
			this.isAlive = false;
			//�������
			this.taskQueue.clear();
			//��ֹ�̳߳��������߳�
			this.interrupt();
		}
	}

	/**
	 * �ر��̳߳أ����ȴ��̳߳��е��������������ꡣ
	 * ���ǲ��ܽ����µ�����
	 */
	public void join() {
		//֪ͨ�����ȴ��̡߳����̳߳��ѹرա�����Ϣ
		synchronized (this) {
			isAlive = false;
			notifyAll();
		}
		// �ȴ������߳����
		// ���Ƚ���һ���µ��߳����顣activeCount������ȡ�̳߳��л�̵߳Ĺ�����
		Thread[] threads = new Thread[this.activeCount()];
		// ���̳߳��еĻ�߳̿������´������߳�����threads�С�
		int count = this.enumerate(threads);
		for (int i = 0; i < count; i++) {
			try {
				// �ȴ��߳����н���
				threads[i].join();
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * �ڲ��࣬����ִ������Ĺ����߳�
	 */
	private class PooledThread extends Thread {

		//���췽��
		public PooledThread() {
			//��һ������Ϊ���߳����ڵ��߳�����󣬼���ǰ�̳߳ض���
			//�ڶ�������Ϊ�߳�����
			super(MyThreadPool.this, "PooledThread-" + (threadID++));
		}

		public void run() {
			//������߳�û�б���ֹ
			while (!isInterrupted()) {

				// ��ȡ����
				Task task = null;
				try {
					task = getTask();
				} catch (InterruptedException ex) {
				}

				//ֻҪ�̳߳ص������б�Ϊ�գ�getTask�������ܵõ�һ������
				//��getTask()����null�����ʾ�̳߳����Ѿ�û�����񣬶����̳߳��ѱ��رա�
				if (task == null) {
					return;
				}

				// �������������쳣
				try {
					task.perform();
				} catch (Throwable t) {
					//���߳����е��߳���δ��������쳣����ʱ��JVM�ͻ�ȥ����uncaughtException������
					uncaughtException(this, t);
				}
			}
		}
	}
}