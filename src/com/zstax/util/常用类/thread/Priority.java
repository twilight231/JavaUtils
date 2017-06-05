package com.zstax.util.常用类.thread;


public class Priority {
	
	static class MyThread extends Thread{
		private int ID = 0;
		public MyThread(int id){
			this.ID = id;
		}
		public void run(){
			System.out.println("MyThread-" + this.ID + 
					" begin! Priority: " + this.getPriority());

			System.out.println("MyThread-" + this.ID + " end!");
		}
	}

	public static void main(String[] args) {
		//����3�����ȼ���ͬ���߳�
		MyThread[] myThreads = new MyThread[3];
		for (int i=0; i<3; i++){
			myThreads[i] = new MyThread(i+1);
			//�����̵߳����ȼ��ֱ���1��4��7
			myThreads[i].setPriority(i*3+1);
		}
		//�����ȼ��ӵ͵��������߳�
		for (int i=0; i<3; i++){
			myThreads[i].start();
		}
		//���������̲߳�һ�������У�������ῼ���̵߳����ȼ���ͬ������£����ȼ��ߵ��߳�������
	}
}
