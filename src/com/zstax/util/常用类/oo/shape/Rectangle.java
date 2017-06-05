package com.zstax.util.常用类.oo.shape;

/**
 * ������
 */
public class Rectangle extends MyShape {
	//�����εĳ�
	private double length;
	//�����εĿ�
	private double width;
	//�߳�������Ϣ
	public static final String SIDEERR = "�����εĳ��Ϳ�������0��";
	/**
	 *Ĭ�Ϲ��캯��
	 */
	public Rectangle(){
		init();
	}
	/**
	 * �ó��Ϳ���һ��������
	 * @param a	����ֵ
	 * @param b ���ֵ
	 */
	public Rectangle(double a, double b){
		if ((a <= 0) || (b <= 0)){
			System.err.println(SIDEERR);
			init();
		} else {
			this.length = a;
			this.width = b;
		}
	}
	/**
	 * ȱʡ�ĳ�����
	 */
	private void init(){
		this.length = 5;
		this.width = 4;
	}
	
	public double getGirth() {
		return (this.length + this.width) * 2;
	}
	public double getArea() {
		return this.length * this.width;
	}
	public String toString() {
		return "���ε������ǣ�" + this.name + "����Ϊ" + this.length + "����Ϊ" + this.width;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		if (length > 0){
			this.length = length;
		} else {
			System.err.println(SIDEERR);
		}
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		if (width > 0) {
			this.width = width;
		} else {
			System.err.println(SIDEERR);
		}
	}
	public static void main(String[] args) {
		Rectangle test = new Rectangle();
		test.setName("myRectangle");
		System.out.println( test.toString());
		System.out.println("���ε��ܳ��ǣ�" + test.getGirth());
		System.out.println("���ε�����ǣ�" + test.getArea());
	}
}
