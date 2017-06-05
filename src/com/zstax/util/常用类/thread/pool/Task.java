package com.zstax.util.常用类.thread.pool;

/**
 * ��������Ľӿ���
 */
public interface Task {

	/**
	 * ִ������
	 * @throws Exception ִ�й����п��ܳ��ֵ��쳣
	 */
	public void perform() throws Exception;
}
