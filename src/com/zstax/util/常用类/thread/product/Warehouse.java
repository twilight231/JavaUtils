package com.zstax.util.常用类.thread.product;
/**
 * ��Ʒ�Ĳֿ���
 * �ڲ�������������ʾѭ�����У��Դ�Ų�Ʒ��
 */
public class Warehouse {
	//�ֿ������
	private static int CAPACITY = 11;
	//�ֿ���Ĳ�Ʒ
	private Product[] products;
	
	//[front, rear)����Ĳ�Ʒ��δ�����ѵ�
	//��ǰ�ֿ��е�һ��δ�����ѵĲ�Ʒ���±�
	private int front = 0;
	//�ֿ������һ��δ�����ѵĲ�Ʒ���±��1
	private int rear = 0;

	public Warehouse(){
		this.products = new Product[CAPACITY];
	}
	public Warehouse(int capacity) {
		this();
		if (capacity > 0){
			CAPACITY = capacity + 1;
			this.products = new Product[CAPACITY];
		}
	}
	/**
	 * �Ӳֿ��ȡһ����Ʒ
	*/
	public Product getProduct() throws InterruptedException {
		synchronized (this) {
			//��־�������߳��Ƿ�������
			boolean consumerRunning = true;
			//��ȡ��ǰ�߳�
			Thread currentThread = Thread.currentThread();
			if (currentThread instanceof Consumer){
				consumerRunning = ((Consumer)currentThread).isRunning();
			} else {
				//�������߲��ܻ�ȡ��Ʒ
				return null;
			}
			//����ֿ���û�в�Ʒ�������������̻߳������У����������̼߳����ȴ���
			while ((front == rear) && consumerRunning){
				wait();
				consumerRunning = ((Consumer)currentThread).isRunning();
			}
			//����������߳��Ѿ�û�������ˣ����˳��÷�����ȡ����ȡ��Ʒ
			if (!consumerRunning){
				return null;
			}
			//ȡ��ǰδ�����ѵĵ�һ����Ʒ
			Product product = products[front];
			System.out.println("Consumer[" + currentThread.getName()
					+ "] getProduct: " + product);
			//����ǰδ�����Ѳ�Ʒ���±����һλ�������������ĩβ�����ƶ����ײ���
			front = (front + 1 + CAPACITY) % CAPACITY;
			System.out.println("�ֿ��л�û�б����ѵĲ�Ʒ������"
					+ (rear + CAPACITY - front) % CAPACITY);
			//֪ͨ�����ȴ��߳�
			notify();
			return product;
		}
	}
	/**
	 * ��ֿ�洢һ����Ʒ
	 */
	public void storageProduct(Product product) throws InterruptedException {
		synchronized (this) {
			//��־�������߳��Ƿ�������
			boolean producerRunning = true;
			//��ȡ��ǰ�߳�
			Thread currentThread = Thread.currentThread();
			if (currentThread instanceof Producer){
				producerRunning = ((Producer)currentThread).isRunning();
			} else {
				//���������߲��ܴ洢��Ʒ
				return;
			}
			//������һ��δ�����Ѳ�Ʒ���һ��δ�����ѵĲ�Ʒ���±�����ţ�
			//��˵��û�д洢�ռ䣬���û�д洢�ռ�����������̻߳������У���ȴ��ֿ��ͷŲ�Ʒ��
			while (((rear + 1) % CAPACITY == front) && producerRunning) {
				wait();
				producerRunning = ((Producer)currentThread).isRunning();
			}
			//����������߳��Ѿ�ֹͣ�ˣ���ֹͣ��Ʒ�Ĵ洢��
			if (!producerRunning){
				return;
			}
			//���������Ʒ���ֿ�
			products[rear] = product;
			System.out.println("Producer[" + Thread.currentThread().getName()
					+ "] storageProduct: " + product);
			//��rear�±�ѭ������һλ
			rear = (rear + 1) % CAPACITY;
			System.out.println("�ֿ��л�û�б����ѵĲ�Ʒ������"
					+ (rear + CAPACITY - front) % CAPACITY);
			notify();
		}
	}
}
