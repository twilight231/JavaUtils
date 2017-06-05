package com.zstax.util.常用类.oo;

public class PassParamter {

	public PassParamter() {
	}

	public void methodA(ComplexNumber comNum) {
		// comNum�������ָ����һ����new�����Ķ���
		comNum = new ComplexNumber(1, 2);
	}

	public void methodB(ComplexNumber comNum) {
		// comNum�������ָ��Ļ���ͬһ�����󣬵��ǣ������޸ĸö����ֵ��
		comNum.setRealPart(1);
		comNum.setRealPart(2);
	}

	public void methodC(int num) {
		// ��num��ֵ��1
		num++;
	}

	public static void main(String[] args) {

		PassParamter test = new PassParamter();
		ComplexNumber comNum = new ComplexNumber(5, 5);
		System.out.println("����methodA����֮ǰ��comNum: " + comNum.toString());
		test.methodA(comNum);
		System.out.println("����methodA����֮��comNum: " + comNum.toString());
		System.out.println("����methodB����֮ǰ��comNum: " + comNum.toString());
		test.methodB(comNum);
		System.out.println("����methodB����֮��comNum: " + comNum.toString());
		int num = 0;
		System.out.println("����methodC����֮ǰ��num: " + num);
		test.methodC(num);
		System.out.println("����methodC����֮��num: " + num);

//		 ����methodA����֮ǰ��comNum: 5.0 + 5.0i
//		 ����methodA����֮��comNum: 5.0 + 5.0i
//		 ����methodB����֮ǰ��comNum: 5.0 + 5.0i
//		 ����methodB����֮��comNum: 2.0 + 5.0i
//		 ����methodC����֮ǰ��num: 0
//		 ����methodC����֮��num: 0

		/**
		 * Java�Ĳ������ݲ��ԣ�
		 * 0����ν��ֵ����˼�ǣ��ڵ��÷���ʱ����������ֵ����һ�ݣ����������ø���������������ԭ���������ı��ˣ���Ӱ��ԭ����ԭ���ı��ˣ���Ӱ�츴����
		 * 1�����ڻ����������ͣ�����int��long�����ͣ����ô�ֵ�Ĳ��ԡ���������ֵ����һ�ݺ󴫸��������������Ըı����ֵ�ĸ�����������ı����ֵ��ԭ����
		 * 2�����ڶ������ͣ�Ҳ���õĴ�ֵ���ԣ��������ǽ����������ø��ƣ�2������ָ��ͬһ������һ�ݴ����������������Ըı�������õĸ�����������ı�������õ�ԭ����
		 * 
		 * �������������
		 * 1������methodA����ʱ�����Ƚ�comNum��������ø���һ�ݣ������õĸ������ݸ�methodA������main�����������õ�ԭ������ʱ������ԭ�������ø�����ָ��comNum����
		 * ִ��comNum = new ComplexNumber(1,2);���󣬽����ø���ָ��������һ���µĶ��󣬴�ʱ�����ø���������ԭ��ָ����ǲ�ͬ�Ķ��󡣻ص�main����ʱ����������Ȼ������ԭ��ָ���comNum����
		 * 2������methodB����ʱ�����Ƚ�comNum��������ø���һ�ݣ������õĸ������ݸ�methodB������main�����������õ�ԭ������ʱ��������ԭ�������ø�����ָ��comNum����
		 * ִ��comNum.setRealPart(1);comNum.setRealPart(2);�����޸������ø�����ָ��������ݣ�����ʱ�����ø���������ԭ��ָ�����ͬһ��������ˣ�����ԭ��ָ��Ķ��������Ҳ�ı��ˡ��ص�main����ʱ��comNum����Ҳ�ı��ˡ�
		 * 3������methodC����ʱ�����Ƚ�num��ֵ����һ�ݣ���ֵ�ĸ������ݸ�methodC������main��������ֵ��ԭ���� ִ��num
		 * ++;���󣬸�����ֵ���1���ص�main����ʱ����������Ȼ��ԭ����ֵ���ֲ��䡣
		 */

	}
}
