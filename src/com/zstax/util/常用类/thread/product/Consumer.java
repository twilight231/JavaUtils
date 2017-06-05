package com.zstax.util.常用类.thread.product;

/**
 *�����ߣ������̣߳�ģ����������Ϊ
 */
class Consumer extends Thread {
	//�����߻�ȡ��Ʒ�Ĳֿ�
	private Warehouse warehouse;
	//�Ƿ���Ҫ�����̵߳ı�־λ
	private boolean running = false;

	public Consumer(Warehouse warehouse, String name) {
		super(name);
		this.warehouse = warehouse;
	}

	public void start(){
		this.running = true;
		super.start();
	}
	
	public void run() {
		Product product;
		try {
			while (running) {
				//�Ӳֿ��л�ȡ��Ʒ
				product = warehouse.getProduct();
				sleep(500);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * ֹͣ�������߳�
	 */
	public void stopConsumer(){
		synchronized (warehouse){
			this.running = false;
			//֪ͨ�ȴ��ֿ���߳�
			warehouse.notifyAll();
		}
	}
	//�������߳��Ƿ�������
	public boolean isRunning() {
		return running;
	}
	
	
}
