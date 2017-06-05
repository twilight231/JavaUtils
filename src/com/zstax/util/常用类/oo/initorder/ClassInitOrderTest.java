package com.zstax.util.常用类.oo.initorder;

public class ClassInitOrderTest {

	public static void main(String[] args) {
		System.out.println("��new���󣬷��ʾ�̬����ʱ�������");
		Child.dispB();
		System.out.println();
		System.out.println("new���󣬷��ʷǾ�̬����ʱ�������");
		new Child().display();
		//֪ͨ�����������������
		System.gc();
	}

//����main������ǰ2�г��򣬽������ע�ͣ��õ��������������£�
//	��new���󣬷��ʾ�̬����ʱ�������
//	Parent�ľ�̬��ʼ����
//	Parent��getNext(int base)������
//	Parent��getNext(int base)������
//	Child�ľ�̬��ʼ����
//	Child��dispB()������
	
//��main������ǰ3�г���ע�ͣ���������ĳ��򣬵õ������������£�
//	new���󣬷��ʷǾ�̬����ʱ�������
//	Parent�ľ�̬��ʼ����
//	Parent��getNext(int base)������
//	Parent��getNext(int base)������
//	Child�ľ�̬��ʼ����
//	Parent�ĳ�ʼ����
//	Parent��getNext(int base)������
//	Parent�Ĺ��췽��������
//	Child�ĳ�ʼ����
//	Child�Ĺ��췽��������
//	Parent��display����������
//	ix=50; iz=31
//	Parent��dispA()������
//	Child�����ٷ���������
//	Parent�����ٷ���������
	
//	�ܽ᣺
//	1����������״μ���Java��ʱ����Ծ�̬��ʼ���顢��̬��Ա��������̬��������һ�γ�ʼ��
//	2��ֻ���ڵ���new����ʱ�Żᴴ�����ʵ��
//	3����ʵ���������̣����ո��Ӽ̳й�ϵ���г�ʼ��������ִ�и���ĳ�ʼ���鲿�֣�Ȼ���Ǹ���Ĺ��췽������ִ��
//	   ����̳е�����ĳ�ʼ���飬���������Ĺ��췽��
//	4����ʵ������ʱ�������������ಿ�֣������ٸ��ಿ��

}
