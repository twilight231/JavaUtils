package com.zstax.util.常用类.thread;

public class ListAllRunningThread {
	/**
	 * �г������̵߳���Ϣ
	 */
	public static void list(){
		//��ȡ��ǰ�߳������߳���ĸ��߳���
	    ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
	    //����ѭ����ֱ���ҵ����߳���
	    while (root.getParent() != null) {
	        root = root.getParent();
	    }
	    //���ʸ��߳����µ��߳�
	    visit(root, 0);
	}
    /**
     * �ݹ����ʾ�߳����е��߳�
     * @param group
     * @param level
     */
    private static void visit(ThreadGroup group, int level) {
        // ��ȡgroup�߳����л�̵߳Ĺ�����
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads];
        // �Ѵ��߳����е����л�̸߳��Ƶ�ָ�������С�
        // false��ʾ��������Ϊ���߳����������߳����е��̡߳�
        numThreads = group.enumerate(threads, false);
    
        // ������߳����飬��ӡ���ǵ�����
        for (int i=0; i<numThreads; i++) {
            // Get thread
            Thread thread = threads[i];
            for (int j=0; j<level; j++){
                System.out.print("  ");
            }
            System.out.println("" + thread.getName());
        }
    
        // ��ȡgroup�߳����л���߳���Ĺ�����
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups];
        // �ѶԴ��߳����е����л��������ø��Ƶ�ָ�������С�
        numGroups = group.enumerate(groups, false);
    
        // �ݹ�ķ������߳����е��߳�
        for (int i=0; i<numGroups; i++) {
            visit(groups[i], level+1);
        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//����һ���߳���
		ThreadGroup group1 = new ThreadGroup("ThreadGroup-1");
		//����3���̲߳�����
		Thread[] threads1 = new Thread[3];
		for (int i=1; i<4; i++){
			//�µ��߳�����group1�߳��飬��ThreadAΪ���ж�������Ϊ"group1-ThreadA-i"
			threads1[i-1] = new Thread(group1, new ThreadA(i*2000), "group1-ThreadA-" + i);
			threads1[i-1].start();
		}
		//������һ���߳��飬����group1�߳���
		ThreadGroup group2 = new ThreadGroup(group1, "ThreadGroup-2");
		//����3���̲߳�����
		Thread[] threads2 = new Thread[3];
		for (int i=1; i<4; i++){
			//�µ��߳�����group2�߳��飬��ThreadAΪ���ж�������Ϊ"group2-ThreadA-i"
			threads2[i-1] = new Thread(group2, new ThreadA(i*1000), "group2-ThreadA-" + i);
			threads2[i-1].start();
		}
		//�г����л���̵߳�����
	    System.out.println("��ǰ������������������е��̣߳�");
		ListAllRunningThread.list();
	}
	
	static class ThreadA extends Thread{
		private long sleepTime = 100;
		public ThreadA(long time){
			this.sleepTime = time;
		}
		public void run(){
			try {
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
