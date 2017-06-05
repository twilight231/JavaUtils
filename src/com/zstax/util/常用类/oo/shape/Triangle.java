package com.zstax.util.常用类.oo.shape;

/**
 * ������
 */
public class Triangle extends MyShape{

	//��a�ĳ���
	private double sideA;
	//��b�ĳ���
	private double sideB;
	//��c�ĳ���
	private double sideC;
	//�߳�����
	public static final String SIDEERR = "�����εı߳����ܹ�С��0��";
	//���ܹ��������δ���
	public static final String SHAPEERR = "�����εĶ���֮�ͱ�����ڵ����ߣ�";
	/**
	 *Ĭ�Ϲ��캯��
	 */
	public Triangle(){
		init();
	}
	/**
	 * �������߹���һ��������
	 * @param a	��a�ı߳�
	 * @param b ��b�ı߳�
	 * @param c ��c�ı߳�
	 */
	public Triangle(double a, double b, double c){
		//����������������ܹ���������Σ����ø����ı߳�����������
		if (isTrianglelegal(a, b, c)){
			this.sideA = a;
			this.sideB = b;
			this.sideC = c;
		} else {
			//������Ĭ��ֵ����������
			init();
		}
	}
	/**
	 * ȱʡ��������
	 */
	private void init(){
		this.sideA = 3;
		this.sideB = 4;
		this.sideC = 5;
	}
	/**
	 * �ж��������Ƿ��ܹ����һ��������
	 * @param a ��a�ı߳�
	 * @param b ��b�ı߳�
	 * @param c ��c�ı߳�
	 * @return �������ͣ�����ܹ���������Σ��㷵��true�����򷵻�false
	 */
	private boolean isTrianglelegal(double a, double b, double c){
		//�����ߵĳ��ȱ������0
		if ((a <= 0) || (a <= 0) || (a <= 0)){
			System.err.println(SIDEERR);
			return false;
		} else if ((a + b < c) || (a + c < b) || (b + c < a)){
			//����֮�ͱ�����ڵ�����
			System.err.println(SHAPEERR);
			return false;
		}
		return true;
	}
	public double getGirth() {
		return this.sideA + this.sideB + this.sideC;
	}
	public double getArea() {
		//���ݺ��׹�ʽ���������ε����
		double s = (this.sideA + this.sideB + this.sideC) / 2;
		//����Math.sqrt()�������п�������
		return Math.sqrt(s * (s - this.sideA) * (s - this.sideB) * (s - this.sideC));
	}
	public String toString() {
		return "�����ε������ǣ�" + this.name + ", ���������ߵı߳��ֱ��ǣ�" 
				+ this.sideA + ", " + this.sideB + ", " + this.sideC;
	}
	public double getSideA() {
		return sideA;
	}
	public void setSideA(double sideA) {
		//�����ñ߳���ʱ����ȻҪ�������µı߳����Ƿ��ܹ���������Ρ�
		if (this.isTrianglelegal(sideA, this.sideB, this.sideC)){
			this.sideA = sideA;
		} 
	}
	public double getSideB() {
		return sideB;
	}
	public void setSideB(double sideB) {
		if (this.isTrianglelegal(this.sideA, sideB, this.sideC)){
			this.sideB = sideB;
		}
	}
	public double getSideC() {
		return sideC;
	}
	public void setSideC(double sideC) {
		if (this.isTrianglelegal(this.sideA, this.sideB, sideC)){
			this.sideC = sideC;
		}
	}
	public static void main(String[] args) {
		Triangle test = new Triangle();
		test.setName("myTriangle");
		System.out.println(test.toString());
		System.out.println("�����ε��ܳ��ǣ�" + test.getGirth());
		System.out.println("�����ε�����ǣ�" + test.getArea());
	}
}
