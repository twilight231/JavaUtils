package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.Exchanger;

/**
 * Exchanger�������߳̿��Ի�����Ϣ��
 * �����з������߳����յı����ﵹˮ���˿��̴߳�װ��ˮ�ı������ˮ��
 * Ȼ��ͨ��Exchanger˫���������ӣ��������������ձ����ﵹˮ���˿ͽ��ź�ˮ��
 * Ȼ�󽻻�������ܶ���ʼ��
 */
public class ExchangerTest {

	// ����һ��װˮ�ı���
	public static class Cup{
		// ��ʶ�����Ƿ���ˮ
		private boolean full = false;
		public Cup(boolean full){
			this.full = full;
		}
		// ��ˮ��������Ҫ5s
		public void addWater(){
			if (!this.full){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				this.full = true;
			}
		}
		// ��ˮ��������Ҫ10s
		public void drinkWater(){
			if (this.full){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
				}
				this.full = false;
			}
		}
	}
	
	public static void testExchanger() {
		//	��ʼ��һ��Exchanger�����涨�ɽ�������Ϣ�����Ǳ���
		final Exchanger<Cup> exchanger = new Exchanger<Cup>();
		// ��ʼ��һ���յı��Ӻ�װ��ˮ�ı���
		final Cup initialEmptyCup = new Cup(false); 
		final Cup initialFullCup = new Cup(true);

		//�������߳�
		class Waiter implements Runnable {
			public void run() {
				Cup currentCup = initialEmptyCup;
				try {
					int i=0;
					while (i < 2){
						System.out.println("��������ʼ����������ˮ��"
								+ System.currentTimeMillis());
						// ���յı������ˮ
						currentCup.addWater();
						System.out.println("��������ˮ��ϣ�"
								+ System.currentTimeMillis());
						// ��������͹˿͵Ŀձ��ӽ���
						System.out.println("�������ȴ���˿ͽ������ӣ�"
								+ System.currentTimeMillis());
						currentCup = exchanger.exchange(currentCup);
						System.out.println("��������˿ͽ���������ϣ�"
								+ System.currentTimeMillis());
						i++;
					}

				} catch (InterruptedException ex) {
				}
			}
		}

		//�˿��߳�
		class Customer implements Runnable {
			public void run() {
				Cup currentCup = initialFullCup;
				try {
					int i=0;
					while (i < 2){
						System.out.println("�˿Ϳ�ʼ��ˮ��"
								+ System.currentTimeMillis());
						//�ѱ������ˮ�ȵ�
						currentCup.drinkWater();
						System.out.println("�˿ͺ�ˮ��ϣ�"
								+ System.currentTimeMillis());
						//���ձ��Ӻͷ������������ӽ���
						System.out.println("�˿͵ȴ���������������ӣ�"
								+ System.currentTimeMillis());
						currentCup = exchanger.exchange(currentCup);
						System.out.println("�˿������������������ϣ�"
								+ System.currentTimeMillis());
						i++;
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		
		new Thread(new Waiter()).start();
        new Thread(new Customer()).start();
	}
	
	public static void main(String[] args) {
		ExchangerTest.testExchanger();
	}
}
