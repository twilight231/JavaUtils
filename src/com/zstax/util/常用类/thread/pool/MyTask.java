package com.zstax.util.常用类.thread.pool;
/**
 * һ���򵥵�����
 */
public class MyTask implements Task{
	/**	�����ID	*/
	private int taskID = 0;
	
	public MyTask(int id){
		this.taskID = id;
	}
	/**
	 * ʵ��Task�ӿڵ�perform������
	 */
	public void perform() throws Exception{
		
        System.out.println("MyTask " + taskID + ": start");
        // ����һ��
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) { 
        }
        System.out.println("MyTask " + taskID + ": end");
    }
}
