package com.zstax.util.常用类.thread.product;
/**
 * �������࣬�����̣߳�ģ�������ߵ���Ϊ
 */
class Producer extends Thread {
	// �����ߴ洢��Ʒ�Ĳֿ�
	private Warehouse warehouse;
	// ��Ʒ������
	private static int produceName = 0;
	//�Ƿ���Ҫ�����̵߳ı�־λ
	private boolean running = false;

	public Producer(Warehouse warehouse, String name) {
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
				//�������洢��Ʒ
				product = new Product((++produceName) + "");
				this.warehouse.storageProduct(product);
				sleep(300);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * ֹͣ�������߳�
	 */
	public void stopProducer(){
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
