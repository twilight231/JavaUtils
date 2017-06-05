package com.zstax.util.常用类.oo;

public class ComplexNumber implements Cloneable{

	/** ������ʵ�� */
	private double realPart;

	/** �������鲿 */
	private double imaginaryPart;

	/** Ĭ�Ϲ��캯�� */
	public ComplexNumber() {
		this.realPart = 0.0;
		this.imaginaryPart = 0.0;
	}

	/**
	 * ���캯��
	 * @param a ʵ��
	 * @param b �鲿
	 */
	public ComplexNumber(double a, double b) {
		this.realPart = a;
		this.imaginaryPart = b;
	}

	/**
	 * �����ļӷ����㡣 
	 * c = a + b�����㷨���ǣ� 
	 * c.ʵ�� = a.ʵ�� + b.ʵ��; c.�鲿 = a.�鲿 + b.�鲿
	 * @param aComNum  ����
	 * @return
	 */
	public ComplexNumber add(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("�����ܹ�Ϊnull��");
			return new ComplexNumber();
		}
		return new ComplexNumber(this.realPart + aComNum.getRealPart(),
				this.imaginaryPart + aComNum.getImaginaryPart());
	}

	/**
	 * �����ļ������㡣 
	 * c = a - b�����㷨���ǣ� 
	 * c.ʵ�� = a.ʵ�� - b.ʵ��; c.�鲿 = a.�鲿 - b.�鲿
	 * @param aComNum  ����
	 * @return
	 */
	public ComplexNumber decrease(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("�����ܹ�Ϊnull��");
			return new ComplexNumber();
		}
		return new ComplexNumber(this.realPart - aComNum.getRealPart(),
				this.imaginaryPart - aComNum.getImaginaryPart());
	}

	/**
	 * �����ĳ˷����㡣 
	 * c = a * b�����㷨���ǣ� 
	 * c.ʵ�� = a.ʵ�� * b.ʵ�� - a.�鲿 * b.�鲿; 
	 * c.�鲿 = a.�鲿 * b.ʵ�� + a.ʵ�� * b.�鲿;
	 * @param aComNum  ����
	 * @return
	 */
	public ComplexNumber multiply(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("�����ܹ�Ϊnull��");
			return new ComplexNumber();
		}
		double newReal = this.realPart * aComNum.realPart - this.imaginaryPart
				* aComNum.imaginaryPart;
		double newImaginary = this.realPart * aComNum.imaginaryPart
				+ this.imaginaryPart * aComNum.realPart;
		ComplexNumber result = new ComplexNumber(newReal, newImaginary);
		return result;
	}

	/**
	 * �����ĳ������㡣 
	 * c = a / b �����㷨���ǣ� 
	 * c.ʵ�� = (a.ʵ�� * b.ʵ�� + a.�鲿 * b.�鲿) / (b.ʵ�� *b.ʵ�� + b.�鲿 * b.�鲿); 
	 * c.�鲿 = (a.�鲿 * b.ʵ�� - a.ʵ�� * b.�鲿) / (b.ʵ�� * b.ʵ�� + b.�鲿 * b.�鲿);
	 * @param aComNum  ����
	 * @return
	 */
	public ComplexNumber divide(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("�����ܹ�Ϊnull��");
			return new ComplexNumber();
		}
		if ((aComNum.getRealPart() == 0) && (aComNum.getImaginaryPart() == 0)) {
			System.err.println("�������ܹ�Ϊ0��");
			return new ComplexNumber();
		}

		double temp = aComNum.getRealPart() * aComNum.getRealPart()
				+ aComNum.getImaginaryPart() * aComNum.getImaginaryPart();
		double crealpart = (this.realPart * aComNum.getRealPart() + this.imaginaryPart
				* aComNum.getImaginaryPart())
				/ temp;
		double cimaginaryPart = (this.imaginaryPart * aComNum.getRealPart() - this.realPart
				* aComNum.getImaginaryPart())
				/ temp;
		return new ComplexNumber(crealpart, cimaginaryPart);
	}

	/**
	 * ��һ��������ʾΪ�ַ���
	 */
	public String toString() {
		return this.realPart + " + " + this.imaginaryPart + "i";
	}

	/**
	 * �Ƚ�һ�������Ƿ��������������ֵ���
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		// �����ж�a�ǲ���һ����������instanceof�ؼ����������ж϶�������͡�
		if (obj instanceof ComplexNumber) {
			// ���a�Ǹ���������Ҫ����ǿ������ת���ɸ������󣬲��ܵ��ø������ṩ�ķ�����
			ComplexNumber b = (ComplexNumber) obj;
			if ((this.realPart == b.getRealPart())
					&& (this.imaginaryPart == b.getImaginaryPart())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * ��øø��������hashcode
	 */
	public int hashCode() {
		// �����������������equals�ģ���ô���ǵ�hashCodeҲ������ͬ��
		// ����ֵ��ȵĸ�������ͨ��toString()�����õ�������ַ�����һ���ģ�
		// ���ǣ����԰ѵõ����ַ�����hashCode�������������hashCode
		return this.toString().hashCode();
	}

	/**
	 * �������ж����¡һ���¶���
	 */
	public Object clone() {
		// �����Ҫʹ�Զ�������ܹ���clone,�ͱ���ʵ��Cloneable�ӿڲ�����д����clone()������
		// ����������д��clone������û����������������ʵ��Cloneable�ӿڣ�����clone����ʱ�������
		// CloneNotSupportedException�쳣�����߿������ԡ�
		try {
			ComplexNumber newObject = (ComplexNumber) super.clone();
			newObject.setRealPart(this.realPart);
			newObject.setImaginaryPart(this.imaginaryPart);
			return newObject;
		} catch (CloneNotSupportedException e) {
			// //���û��ʵ��Cloneable�ӿڣ��׳��쳣
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return ���� imaginaryPart��
	 */
	public double getImaginaryPart() {
		return imaginaryPart;
	}

	/**
	 * @param imaginaryPart  Ҫ���õ� imaginaryPart��
	 */
	public void setImaginaryPart(double imaginaryPart) {
		this.imaginaryPart = imaginaryPart;
	}

	/**
	 * @return ���� realPart��
	 */
	public double getRealPart() {
		return realPart;
	}

	/**
	 * @param realPart  Ҫ���õ� realPart��
	 */
	public void setRealPart(double realPart) {
		this.realPart = realPart;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		ComplexNumber a = new ComplexNumber(2, 4);
		ComplexNumber b = new ComplexNumber(2, 4);
		System.out.println("ComplexNumber a: " + a.toString());
		System.out.println("ComplexNumber b: " + b.toString());

		System.out.println("a.euqals(b) = " + a.equals(b));
		System.out.println("a.hashCode = " + a.hashCode() 
				+ "; b.hashCode = " + b.hashCode());
		System.out.println("a.clone = " + a.clone().toString());

//		System.out.println("(a + b) = " + a.add(b).toString());
//		System.out.println("(a - b) = " + a.decrease(b).toString());
//		System.out.println("(a * b) = " + a.multiply(b).toString());
//		System.out.println("(a / b) = " + a.divide(b).toString());
		
		
//		System.out.println(a.equals(b));
//		ComplexNumber c = new ComplexNumber(1, 2);
//		ComplexNumber d = new ComplexNumber(1.00, 2.0);
//
//		ComplexNumber e = (ComplexNumber) d.clone();
//		System.out.println(e.toString());

	}
}
