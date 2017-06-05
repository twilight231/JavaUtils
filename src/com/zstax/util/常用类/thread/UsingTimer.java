package com.zstax.util.常用类.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ��
 * ��Ӧ�ÿ����У�������ҪһЩ�����ԵĲ���������ÿ5���Ӽ��һ�����ʼ��ȡ�
 * ���������Ĳ�����㡢��Ч��ʵ�ַ�ʽ����ʹ��java.util.Timer�����ࡣ
 */
public class UsingTimer {
	/**
	 * �ҵ������࣬�̳�TimerTask
	 * TimerTask��һ�������࣬����ʵ�����ĳ��󷽷�run()
	 */
	static class MyTask extends TimerTask{
		private int taskID = 0;
		public MyTask(int id){
			this.taskID = id;
		}
		public void run(){
			System.out.println("run MyTask-" + this.taskID 
					+ " at time: " + System.currentTimeMillis());
		}
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		TimerTask myTask1 = new MyTask(1);
		//200ms��ִ��myTask1
		timer.schedule(myTask1, 200);
		//300ms��ִ��myTask2�������Ժ�ÿ��500ms��ִ��һ��myTask2
		TimerTask myTask2 = new MyTask(2);
		timer.schedule(myTask2, 300, 500);
		//��ָ��ʱ�䣨һ����֮��ִ��myTask3
		TimerTask myTask3 = new MyTask(3);
		Date date = new Date(System.currentTimeMillis() + 1000);
		timer.schedule(myTask3, date);
		try {
			//�ȴ�5����
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//��ֹ��ʱ������ȡ����ʱ���е�����
		timer.cancel();
		System.out.println("timer canceled!");
	}
}
