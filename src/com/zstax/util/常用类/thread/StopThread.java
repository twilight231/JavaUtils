package com.zstax.util.常用类.thread;
/**
 * ֹͣ�߳�
 */
public class StopThread {
	/**	�̶߳���	*/
	private ThreadA thread = new ThreadA();
	/**	�Զ����߳��� */
	class ThreadA extends Thread{
		//��һ��booleanֵ����߳��Ƿ���Ҫ���С�
		private boolean running = false;
		//�����˸����start������
		public void start(){
			//��running��Ϊture����ʾ�߳���Ҫ����
			this.running = true;
			super.start();
		}
		public void run(){
			System.out.println("ThreadA begin!");
			int i=0; 
			try {
				//���runningΪ�棬˵���̻߳����Լ�������
				while (running){
					System.out.println("ThreadA: " + i++);
					//sleep��������ǰ�߳����ߡ�
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
			}

			System.out.println("ThreadA end!");
		}
		public void setRunning(boolean running){
			this.running = running;
		}
	}
	/**
	 * ����ThreadA�߳�
	 */
	public void startThreadA(){
		System.out.println("To start ThreadA!");
		thread.start();
	}
	/**
	 * ֹͣThreadA�߳�
	 */
	public void stopThreadA(){
		System.out.println("To stop ThreadA!");
		thread.setRunning(false);
	}
	
	public static void main(String[] args) {
		StopThread test = new StopThread();
		//����ThreadA�߳�
		test.startThreadA();
		//��ǰ�߳�����һ����
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//ֹͣThreadA�߳�
		test.stopThreadA();
	}
}
