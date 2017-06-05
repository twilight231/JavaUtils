package com.zstax.util.常用类.number;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * ��1.4�ڵļ���Factorial�����У������������û������n����С��18��ԭ���ǣ���n����18ʱ��
 * n!��ֵ�Ѿ������˷�Χ���Ļ�������long�ܹ���ʾ�ķ�Χ�ˣ�Ҳ����˵ʹ��Java�Ļ������������ڽ�������ʱ�����������⡣
 * 
 * ͬ�����ڽ���С������ʱ��Ҳ����ֲ���ȷ�����⡣�뿴����һ�δ��룺
 * code
 * ִ�н�������Ի�
 * Java�еļ򵥸���������float��double���ܹ����о�ȷ�����㣬ֻ����������ѧ��������ǹ��̼��㡣
 * ������Java���������ܶ���������Ҳ�����������⡣�ڴ��������£�����Ľ����׼ȷ�ģ�
 * ���Ƕ��Լ��Σ�������һ��ѭ�����Ϳ����Գ���������Ĵ���
 * ��������൱���أ��������9.999999999999Ԫ����ļ�����ǲ�����Ϊ����Թ���10Ԫ����Ʒ�ġ�
 * 
 * Ϊ�˽��Java������������������ʱ����ֵ�����ͼ��㲻��ȷ�����⡣
 * Java �ṩ��������BigInteger��BigDecimal��ר�����ڽ��и߾�������
 * ��������int ��float �������飬��BigInteger��BigDecimalҲ��������
 * ֻ�Ǳ��뻻�÷������ã�������ʹ���������
 * 
 * �߾�������BigInteger
 * BigInteger֧�����⾫�ȵ�������Ҳ����˵���ǿɾ�ȷ��ʾ�����С������ֵ��ͬʱ����������в��ᶪʧ�κ���Ϣ��
 * 
 * �߾��ȸ�����BigDecimal
 * �����Ա�ʾ���⾫�ȵ�С�����������ǽ��м��㡣
 * ���� BigDecimal �����ǲ��ɱ�ģ���Щ�����е�ÿһ����������µ� BigDecimal ����
 * ��ˣ���Ϊ��������Ŀ�����BigDecimal ���ʺ��ڴ�������ѧ���㣬���������Ŀ����������ȷ�ر�ʾС����
 */
public class BigNumber {

	//Ĭ�ϳ������㾫��,������С�������λ 
	private static final int DEFAULT_DIV_SCALE = 10;

	//����಻��ʵ���� 
	private BigNumber() {
	}

	/** 
	 * �ṩ��ȷ�ļӷ����㡣 
	 * @param v1 ������ 
	 * @param v2 ���� 
	 * @return ���������ĺ� 
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.add(b2)).doubleValue();
	}

	/** 
	 * �ṩ��ȷ�ļ������㡣 
	 * @param v1 ������ 
	 * @param v2 ���� 
	 * @return ���������Ĳ� 
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.subtract(b2)).doubleValue();
	}

	/** 
	 * �ṩ��ȷ�ĳ˷����㡣 
	 * @param v1 ������ 
	 * @param v2 ���� 
	 * @return ���������Ļ� 
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.multiply(b2)).doubleValue();
	}

	/** 
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ�� 
	 * С�����Ժ����λ���Ժ�������������롣 
	 * @param v1 ������ 
	 * @param v2 ���� 
	 * @return ������������ 
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/** 
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ 
	 * �����ȣ��Ժ�������������롣 
	 * @param v1 ������ 
	 * @param v2 ���� 
	 * @param scale ��ʾ��Ҫ��ȷ��С�����Ժ�λ�� 
	 * @return ������������ 
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			System.err.println("�������ȱ������0!");
			return 0;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)).doubleValue();
	}

	/**
	 * ����Factorial�׳ˣ�
	 * @param n		������ڵ���0��int
	 * @return     n!��ֵ
	 */
	public static BigInteger getFactorial(int n) {
		if (n < 0) {
			System.err.println("n������ڵ���0��");
			return new BigInteger("-1");
		} else if (n == 0) {
			return new BigInteger("0");
		}
		//�����黻���ַ�������BigInteger
		BigInteger result = new BigInteger("1");
		for (; n > 0; n--) {
			//������nת�����ַ������ٹ���һ��BigInteger���������н�����˷�
			result = result.multiply(new BigInteger(new Integer(n).toString()));
		}
		return result;
	}

	public static void main(String[] args) {

		//		������Ǳ������������������ῴ��ʲô��
		System.out.println(0.05 + 0.01);
		System.out.println(1.0 - 0.42);
		System.out.println(4.015 * 100);
		System.out.println(123.3 / 100);
		//		0.060000000000000005
		//		0.5800000000000001
		//		401.49999999999994
		//		1.2329999999999999

		//����׳ˣ����Խ�n��ø���
		int n = 30;
		System.out.println("����n�Ľ׳�" + n + "! = " + BigNumber.getFactorial(n));

		//��double����BigDecimal
		BigDecimal bd1 = new BigDecimal(0.1);
		System.out.println("(bd1 = new BigDecimal(0.1)) = " + bd1.toString());
		//��String����BigDecimal
		BigDecimal bd2 = new BigDecimal("0.1");
		System.out.println("(bd2 = new BigDecimal(\"0.1\")) = "
				+ bd2.toString());

		BigDecimal bd3 = new BigDecimal("0.10");
		//equals�����Ƚ�����BigDecimal�����Ƿ���ȣ���ȷ���true�����ȷ���false
		System.out.println("bd2.equals(bd3) = " + bd2.equals(bd3));//false
		//compareTo�����Ƚ�����BigDecimal����Ĵ�С����ȷ���0��С�ڷ���-1�����ڷ���1��
		System.out.println("bd2.compareTo(bd3) = " + bd2.compareTo(bd3));//0

		//���о�ȷ����
		System.out.println("0.05 + 0.01 = " + BigNumber.add(0.05, 0.01));
		System.out.println("1.0 - 0.42 = " + BigNumber.add(1.0, 0.42));
		System.out.println("4.015 * 100 =" + BigNumber.add(4.015, 100));
		System.out.println("123.3 / 100 = " + BigNumber.add(123.3, 100));

		/**
		 * ��1��BigInteger��BigDecimal���ǲ��ɱ�(immutable)�ģ��ڽ���ÿһ������ʱ���������һ���µĶ������ڴ����������������
		 * ���ǲ��ʺ��ڴ�������ѧ���㣬Ӧ������long,float,double�Ȼ�����������ѧ������߹��̼��㡣
		 * ���BigInteger��BigDecimal��Ŀ����������ȷ�ر�ʾ��������С����ʹ��������ҵ������ʹ�á�
		 * ��2��BigDecimal��4�����췽�������е�������BigInteger���죬��һ������double���죬����һ��ʹ��String���졣
		 * Ӧ�ñ���ʹ��double����BigDecimal����Ϊ����Щ������double�����޷���ȷ��ʾ������BigDecimal���췽��ʱ���Ѿ�����ȷ�ˡ�
		 * ���磬new BigDecimal(0.1)�õ���ֵ��0.1000000000000000055511151231257827021181583404541015625��
		 * ʹ��new BigDecimal("0.1")�õ���ֵ��0.1����ˣ������Ҫ��ȷ���㣬��String����BigDecimal��������double���죬���������������򵥣� 
		 * ��3��equals()������Ϊ0.1��0.1����ȵģ�����true������Ϊ0.10��0.1�ǲ��ȵģ��������false��
		 * ����compareTo()����Ϊ0.1��0.1��ȣ�0.10��0.1Ҳ��ȡ������ڴ���ֵ�ϱȽ�����BigDecimalֵʱ��Ӧ��ʹ��compareTo()������ equals()��
		 * ��4�����⻹��һЩ���Σ����⾫�ȵ�С�������Բ��ܱ�ʾ��ȷ��������磬1����9���������ѭ����С�� .111111...��
		 * �������ԭ���ڽ��г�������ʱ��BigDecimal����������ʽ�ؿ������롣
		 */

	}

}