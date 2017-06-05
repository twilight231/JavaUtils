package com.zstax.util.常用类.j2se5.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BlockingQueue��һ�������Queue����BlockingQueue�ǿյģ�
 * ��BlockingQueueȡ�����Ĳ������ᱻ��Ͻ���ȴ�״ֱ̬��BlocingkQueue�����»��Żᱻ���ѡ�
 * ͬ�������BlockingQueue�������κ���ͼ����涫���Ĳ���Ҳ�ᱻ��Ͻ���ȴ�״̬��
 * ֱ��BlockingQueue�����µĿռ�Żᱻ���Ѽ���������
 * BlockingQueue�ṩ�ķ�����Ҫ�У�
 * add(anObject): ��anObject�ӵ�BlockingQueue����BlockingQueue�������ɷ���true�������׳�IllegalStateException�쳣�� 
 * offer(anObject)����anObject�ӵ�BlockingQueue����BlockingQueue�������ɷ���true�����򷵻�false�� 
 * put(anObject)����anObject�ӵ�BlockingQueue����BlockingQueueû�пռ䣬���ô˷������̱߳����ֱ��BlockingQueue�����µĿռ��ټ����� 
 * poll(time)��ȡ��BlockingQueue��������λ�Ķ�������������ȡ���ɵ�time�����涨��ʱ�䡣ȡ����ʱ����null�� 
 * take()��ȡ��BlockingQueue��������λ�Ķ�����BlockingQueueΪ�գ���Ͻ���ȴ�״ֱ̬��BlockingQueue���µĶ��󱻼���Ϊֹ��
 * 
 * ���ݲ�ͬ����ҪBlockingQueue��4�־���ʵ�֣�
 * ��1��ArrayBlockingQueue���涨��С��BlockingQueue���乹�캯�������һ��int������ָ�����С���������Ķ�������FIFO�������ȳ���˳������ġ� 
 * ��2��LinkedBlockingQueue����С������BlockingQueue�����乹�캯����һ���涨��С�Ĳ��������ɵ�BlockingQueue�д�С���ƣ�
 * ��������С�����������ɵ�BlockingQueue�Ĵ�С��Integer.MAX_VALUE���������������Ķ�������FIFO�������ȳ���˳������ġ�
 * LinkedBlockingQueue��ArrayBlockingQueue�Ƚ����������Ǳ������õ����ݽṹ��һ����
 * ����LinkedBlockingQueue������������Ҫ����ArrayBlockingQueue�������߳������ܴ�ʱ�����ܵĿ�Ԥ���Ե���ArrayBlockingQueue�� 
 * ��3��PriorityBlockingQueue��������LinkedBlockingQueue���������������������FIFO���������ݶ������Ȼ����˳������ǹ��캯��������Comparator������˳�� 
 * ��4��SynchronousQueue�������BlockingQueue������Ĳ��������Ƿź�ȡ������ɵġ�
 * 
 * ��������BlockingQueue��ʵ��Producer��Consumer������
 */
public class BlockingQueueTest {

	/**
	 * ����װƻ��������
	 */
	public static class Basket{
		// ���ӣ��ܹ�����3��ƻ��
		BlockingQueue<String> basket = new ArrayBlockingQueue<String>(3);
		
		// ����ƻ������������
		public void produce() throws InterruptedException{
			// put��������һ��ƻ������basket���ˣ��ȵ�basket��λ��
			basket.put("An apple");
		}
		// ����ƻ������������ȡ��
		public String consume() throws InterruptedException{
			// get����ȡ��һ��ƻ������basketΪ�գ��ȵ�basket��ƻ��Ϊֹ
			return basket.take();
		}
	}
	//�����Է���
	public static void testBasket() {
		// ����һ��װƻ��������
		final Basket basket = new Basket();
		// ����ƻ��������
		class Producer implements Runnable {
			public void run() {
				try {
					while (true) {
						// ����ƻ��
						System.out.println("������׼������ƻ����" 
								+ System.currentTimeMillis());
						basket.produce();
						System.out.println("����������ƻ����ϣ�" 
								+ System.currentTimeMillis());
						// ����300ms
						Thread.sleep(300);
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		// ����ƻ��������
		class Consumer implements Runnable {
			public void run() {
				try {
					while (true) {
						// ����ƻ��
						System.out.println("������׼������ƻ����" 
								+ System.currentTimeMillis());
						basket.consume();
						System.out.println("����������ƻ����ϣ�" 
								+ System.currentTimeMillis());
						// ����1000ms
						Thread.sleep(1000);
					}
				} catch (InterruptedException ex) {
				}
			}
		}
		
		ExecutorService service = Executors.newCachedThreadPool();
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		service.submit(producer);
		service.submit(consumer);
		// ��������5s����������ֹͣ
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		service.shutdownNow();
	}

	public static void main(String[] args) {
		BlockingQueueTest.testBasket();
	}
}
