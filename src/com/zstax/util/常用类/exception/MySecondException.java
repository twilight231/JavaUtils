package com.zstax.util.常用类.exception;

/**
 * �Զ����쳣��
 * �ڶ��ֶ��巽ʽ���̳�Throwable ��
 * @author new
 *
 */
public class MySecondException extends Throwable {

	public MySecondException() {
		super();
	}

	public MySecondException(String msg) {
		super(msg);
	}

	public MySecondException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MySecondException(Throwable cause) {
		super(cause);
	}
}
