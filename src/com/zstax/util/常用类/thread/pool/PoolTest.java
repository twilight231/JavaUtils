package com.zstax.util.常用类.thread.pool;

/**
 * �����̳߳�
*/
public class PoolTest {

    public static void main(String[] args) {

    	//�̳߳��е��߳���
    	int numThreads = 3;
        // �����̳߳�
        MyThreadPool threadPool = new MyThreadPool(numThreads);

        // ������
        int numTasks = 10;
        // ��������
        for (int i=0; i<numTasks; i++) {
            threadPool.performTask(new MyTask(i));
        }

        // �ر��̳߳ز��ȴ������������
        threadPool.join();
    }
}
