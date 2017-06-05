package com.zstax.util.常用类.exception;

/**
 * �Զ����쳣�ࡣ
 * ��һ�ֶ��巽ʽ���̳�Exception��
 */
public class MyFirstException extends Exception {
	
	public MyFirstException() {
		super();
	}

	public MyFirstException(String msg) {
		super(msg);
	}

	public MyFirstException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MyFirstException(Throwable cause) {
		super(cause);
	}
	//�Զ����쳣�����Ҫ�����������쳣������λ�ã����û������쳣ʱ��
	//�����쳣���Ϳ���֪���������쳣�������쳣��ʾ��Ϣ�����޸ġ�
}
