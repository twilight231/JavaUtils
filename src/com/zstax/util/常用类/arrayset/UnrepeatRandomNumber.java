package com.zstax.util.常用类.arrayset;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * ��ָ���ķ�Χ�ڣ����ɲ��ظ������������
 */
public class UnrepeatRandomNumber {
	private int min;
	private int max;
	public UnrepeatRandomNumber(){
		this.min = 0;
		this.max = 10;
	}
	public UnrepeatRandomNumber(int min, int max){
		this();
		if (max >= min){
			this.min = min;
			this.max = max;
		} else {
			System.out.println("max��minС����ȱʡֵ����UnrepeatRandomNumber����");
		}
	}
	/**
	 * ��һ�ַ������ų���������������֣�����������ɵ����֣���ŵ�����б���
	 * �������Ѿ����ɹ��ģ��򲻼������б�����������ɡ�
	 * @param length	����б�ĳ���
	 * @return
	 */
	public Integer[] getRandomMethodA(int length) {
		if (length <= 0) {
			return new Integer[0];
		} else if (length > (this.max - this.min)) {
			System.out.println("����б��Ȳ��ܴﵽ:" + length + ", �������ֻ���ǣ�"
					+ (this.max - this.min));
			length = this.max - this.min;
		}
		Random rd = new Random();//��������������
		List resultList = new ArrayList();
		while (resultList.size() < length) {
			//��[min, max]����ȼ���min + [0, max - min + 1) 
			Integer randnum = new Integer(this.min + rd.nextInt(this.max - this.min + 1));
			if (!resultList.contains(randnum)){
				resultList.add(randnum);
			}
		}
		//ʹ��toArray������Listת���ɶ������鷵��
		return (Integer[])resultList.toArray(new Integer[0]);
	}
	
	/**
	 * �ڶ��ַ�����ɸѡ���������п��ܱ����ɵ����ַŵ�һ����ѡ�б��С�
	 * Ȼ���������������Ϊ�±꣬����ѡ�б�����Ӧ�±�����ַŵ��ŵ�����б��У�
	 * ͬʱ�������ں�ѡ�б���ɾ����
	 * @param length	����б�ĳ���
	 * @return
	 */
	public Integer[] getRandomMethodB(int length) {
		if (length <= 0) {
			return new Integer[0];
		} else if (length > (this.max - this.min)) {
			System.out.println("����б��Ȳ��ܴﵽ:" + length + ", �������ֻ���ǣ�"
					+ (this.max - this.min));
			length = this.max - this.min;
		}
		//��ʼ����ѡ�б��б���Ϊ max -min + 1
		int candidateLength = this.max - this.min + 1;
		List candidateList = new ArrayList();
		for (int i=this.min; i<= this.max; i++){
			candidateList.add(new Integer(i));
		}
		
		Random rd = new Random();//������������±�
		List resultList = new ArrayList();
		while (resultList.size() < length) {
			//�����±꣬��[0,candidateLength)��Χ��
			int index = rd.nextInt(candidateLength);
			//����ѡ�������±�Ϊindex�����ֶ��������������
			resultList.add(candidateList.get(index));
			//���±�Ϊindex�����ֶ���Ӻ�ѡ������ɾ��
			candidateList.remove(index);
			//��ѡ���г��ȼ�ȥ1
			candidateLength --;
		}
		//ʹ��toArray������Listת���ɶ������鷵��
		return (Integer[])resultList.toArray(new Integer[0]);
	}
	
	public static void outputArray(Integer[] array){
		if (array != null){
			for (int i=0; i<array.length; i++){
				System.out.print(array[i] + "  ");
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		UnrepeatRandomNumber test = new UnrepeatRandomNumber(5, 15);
		outputArray(test.getRandomMethodA(8));
		outputArray(test.getRandomMethodB(8));
		//���֮�£���һ�ַ�������Random�������ɵ�������Ĵ����Ƚ϶࣬����������20�����֣������ҵ�10����һ�������֡�
		//�ڶ��ַ�������Random�������ɵ�������Ĵ����Ƚ��٣���Ҫ���ٸ��������ɶ��ٸ�����֤��ÿ�����ɵ����ֶ����ظ���
		//Ҳ����˵��һ�ַ�����ʱ�仨���ϸ��ࡣ���ǵڶ��ַ�����Ҫ��ʼ��һ����ѡ���У���Ҫ����Ŀռ仨�ѡ�
	}
}
