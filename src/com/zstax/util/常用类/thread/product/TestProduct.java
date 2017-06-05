package com.zstax.util.常用类.thread.product;
/**
 * ���������ߡ���������
 * ֧�ֶ�������ߺ�������
 */
public class TestProduct {

	public static void main(String[] args) {
		//����һ���ֿ⣬����Ϊ10
		Warehouse warehouse = new Warehouse(10);
		
		//���������ߺ�������
		Producer producers1 = new Producer(warehouse, "producer-1");
		Producer producers2 = new Producer(warehouse, "producer-2");
		Producer producers3 = new Producer(warehouse, "producer-3");
		Consumer consumers1 = new Consumer(warehouse, "consumer-1");
		Consumer consumers2 = new Consumer(warehouse, "consumer-2");
		Consumer consumers3 = new Consumer(warehouse, "consumer-3");
		Consumer consumers4 = new Consumer(warehouse, "consumer-4");
		//���������ߺ��������߳�
		producers1.start();
		producers2.start();
		consumers1.start();
		producers3.start();
		consumers2.start();
		consumers3.start();
		consumers4.start();
		//�������������߳�������1600ms
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//ֹͣ�����ߺ������ߵ��߳�
		producers1.stopProducer();
		consumers1.stopConsumer();
		producers2.stopProducer();
		consumers2.stopConsumer();
		producers3.stopProducer();
		consumers3.stopConsumer();
		consumers4.stopConsumer();
	}
}
