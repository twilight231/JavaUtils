package com.zstax.util.常用类.arrayset;

import java.util.LinkedList;

/**
 * ʵ�ֶ��й���
 * ����Ԫ��ֻ�ܴӶ�β���룬�Ӷ���ȡ����
 * �ڶ����У�����Ԫ�ؿ�������������������Ԫ�صĴ��򲻻�ı䡣
 * ÿ��������Ԫ�شӶ����б�ȡ�������������Ԫ��������ǰ�ƶ�һλ��
 * ���ԣ��κ�ʱ��Ӷ����ж����Ķ��Ƕ��׵����ݡ�
 */
public class Queue {
	
	private LinkedList data = new LinkedList();
	
	public Queue(){
	}
	/**
	 * ��������һ��Ԫ�أ�ֻ�ܼ��뵽��β
	 * @param obj
	 */
	public void add(Object obj){
		this.data.addLast(obj);
	}
	/**
	 * �鿴����Ԫ�أ����ݻ������ڶ�����
	 * @return
	 */
	public Object peek(){
		if (data.isEmpty()){
			System.out.println("������û��Ԫ�أ�");
			return null;
		}
		return data.getFirst();
	}
	/**
	 * ɾ������Ԫ��
	 * @return
	 */
	public boolean remove(){
		if (data.isEmpty()){
			System.out.println("������û��Ԫ�أ�");
			return false;
		}
		data.removeFirst();
		return true;
	}
	/**
	 * ����Ԫ�أ�����ȡ����Ԫ�ز�����Ӷ�����ɾ��
	 * @return
	 */
	public Object pop(){
		if (data.isEmpty()){
			System.out.println("������û��Ԫ�أ�");
			return null;
		}
		return data.removeFirst();
	}
	/**
	 * 	�ڶ����в���Ԫ�أ����ص�һ�γ��ֵ�λ��
	 * @param obj
	 * @return  
	 */
	public int indexOf(Object obj){
		return data.indexOf(obj);
	}
	/**
	 * �ڶ����в���Ԫ�أ��������һ�γ��ֵ�λ��
	 * @param obj
	 * @return
	 */
	public int lastIndexOf(Object obj){
		return data.lastIndexOf(obj);
	}
	/**
	 * �ж϶����Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return data.isEmpty();
	}
	/**
	 * �������������Ԫ��
	 */
	public void clear(){
		data.clear();
	}

	public static void main(String[] args) {
		Queue myQueue = new Queue();
		//����в���Ԫ��
		myQueue.add("aaa");
		myQueue.add("bbb");
		myQueue.add("ccc");
		myQueue.add("bbb");
		myQueue.add("ccc");
		myQueue.add("bbb");
		//��ȡ��һ��Ԫ��
		System.out.println("���׵�������: " + myQueue.peek());
		//ɾ��Ԫ��
		myQueue.remove();
		//��ȡ��һ��Ԫ�ز�ɾ��
		System.out.println("�Ӷ��е�������: " + myQueue.pop());
		//����bbb��һ�γ��ֵ�λ��
		System.out.println("bbb��һ���ڶ����г��ֵ�λ��" + myQueue.indexOf("bbb"));
		//����bb���һ�γ��ֵ�λ��
		System.out.println("bbb���һ���ڶ����г��ֵ�λ��" + myQueue.lastIndexOf("bbb"));
		//��������е�����Ԫ��
		myQueue.clear();
		//�ж϶����е�Ԫ�ظ����Ƿ�Ϊ0
		System.out.println("�����е�Ԫ�ظ���Ϊ0�� " + myQueue.isEmpty());
		
		/**
		 * ��Ϊ���о�����Ҫ�Զ��׺Ͷ�β�����ݽ��в������ڶ��ײ����ɾ��Ԫ�أ�
		 * ��������Ҫ���������м�����ݡ�
		 * ��ˣ����ʺ�ʹ��ArrayList��Vector����Ϊ����ʹ������ʵ�ֵģ�Ԫ�صĲ����ɾ��������������ĸ��ƣ������ϴ�
		 * ��������ѡ��LinkedList��Ŷ��е����ݣ���������ṹ�洢���ݣ����ʺ�Ԫ�صĲ����ɾ����
		 */
	}
}
