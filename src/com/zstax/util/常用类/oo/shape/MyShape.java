package com.zstax.util.常用类.oo.shape;
/**
 * �Զ���ͼ�εĻ���
 */
public abstract class MyShape {
	/**ͼ�ε�����*/
	protected String name; //protected���ʿ��Ʒ���ʾֻ�б���������ܹ����ʸ����ԡ�
	/**
	 * ���󷽷�����ȡ��״���ܳ�
	 */
	public abstract double getGirth();
	/**
	 * ���󷽷�����ȡ��״�����
	 */
	public abstract double getArea();
	/**
	 * ���󷽷��������״
	 */
	public abstract String toString();
	/**
	 * ��ȡͼ�ε�����
	 * @return ����ͼ�ε�����
	 */	
	public String getName(){
		return this.name;
	}
	/**
	 * Ϊͼ����������
	 * @param name	Ҫ���õ�ͼ�ε�����
	 */
	public void setName(String name){
		this.name = name;
	}
}
