package com.zstax.util.常用类.exception;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * ʹ���쳣�ļ���ע��
 */
public class ExceptionTips {

	public static void main(String[] args) {

		//��1����������ʹ���쳣�����쳣�����ǰ��������
		Stack stack = new Stack();
		try {
			stack.pop();
		} catch (EmptyStackException e){
			//...
		}
		//Ӧ��ʹ������ķ�ʽ���Ա���ʹ���쳣
		if (!stack.isEmpty()){
			stack.pop();
		}
		
		//��2����ҪΪÿ�����ܻ�����쳣��䶼����try��catch��
		try {
			stack.pop();
		} catch (EmptyStackException e){
			//...
		}
		String data = "123";
		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e){
			//...
		}
		//Ӧ��ʹ������ķ�ʽ��������������һ��try����
		try {
			stack.pop();
			Double.parseDouble(data);
		} catch (EmptyStackException e){
			//...
		} catch (NumberFormatException e){
			//...
		}
		
		//��3�������ڷ�����throw���߲�������ʱ�쳣RuntimeException�������ڴ����ȡ�
		//���������������
		String[] array;
		try {
			array = new String[1000];
			//array = new String[10000000];��ʱ�����OutOfMemoryError�쳣
		} catch (OutOfMemoryError e){
			throw e;
		}
		//ֱ��������Ĵ���
		array = new String[1000];
		
		//��4����������catch Exception��Throwable����Ҫcatch������쳣���������Ը��ݲ�ͬ���쳣����ͬ�Ĵ����ǳ������������
		try {
			stack.pop();
			Double.parseDouble(data);
		} catch (Exception e){
			//Ӧ�ñ���catch Exception!!!
		}
		//��5����Ҫѹ�ơ������쳣�������ܴ�����쳣�����ף�������catchס֮����㴦��
		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e){
			//...
			throw e; //�׳����ܴ�����쳣��������������
		}
		//��6����Ҫ��ѭ����ʹ��try catch��������try catch����ѭ������߱���ʹ��try catch��
		//�뿴��������ӣ���ѭ����ʹ��try��catch���ķѸ����ʱ�䣬����û���쳣����
		int i = 0;
		int ntry = 1000000;
		Stack s = new Stack();
		long s1;
		long s2;
		System.out.println("Testing for empty stack");
		s1 = new Date().getTime();
		for (i = 0; i <= ntry; i++){
			if (!s.empty()){
				s.pop();
			}
		}
		s2 = new Date().getTime();
		System.out.println((s2 - s1) + " milliseconds");

		System.out.println("Catching EmptyStackException");
		s1 = new Date().getTime();
		for (i = 0; i <= ntry; i++){
			try {
				s.pop();
			} catch (EmptyStackException e){
			}
		}
		s2 = new Date().getTime();
		System.out.println((s2 - s1) + " milliseconds");
	}
}
