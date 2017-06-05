package com.zstax.util.常用类.applet.tower;

/**
 * �������ƶ���ŵ���Ĳ���
 */
public class Params {
	// Ҫ�ƶ�������
	public int num;
	// �ƶ�����ʼ��
	public int from;
	// �ƶ���Ŀ����
	public int to;
	// �ƶ�����ת��
	public int inter;
	// ����������
	public int actionCode;
	
	public Params(int num, int from, int to, int inter, int codePart) {
		this.num = num;
		this.from = from;
		this.to = to;
		this.inter = inter;
		this.actionCode = codePart;
	}
}