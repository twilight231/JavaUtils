package com.zstax.util.常用类.number;

import java.math.BigDecimal;
/**
 * �����ֽ��и���ģʽ�����룬�����������
 */
public class Round {
	/**
	 * ����java.lang.Math������ֽ�����������
	 * @param dou	�����������
	 * @return
	 */
	public static long getTraRoundMath(double dou) {
		//Math.round�����������Ƚ�dou����0.5��Ȼ��ȡ��������
		//dou = 4.6, ����dou��0.5��5.1����������Ϊ5����������Ľ������5��
		return Math.round(dou);
	}
	/**
	 * �����ֽ�����������
	 * @param dou	�����������
	 * @return
	 */
	public static long getTraRound(double dou) {
		//��������ģʽ�൱��BigDecimal.ROUND_HALF_UPģʽ
		return Round.getIntRound(dou, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * Ҫ������󷵻��������ͣ�
	 * 
	 * @param dou  �����������
	 * @param roundmode  ����ģʽ
	 * @return
	 */
	public static long getIntRound(double dou, int roundmode) {
		//	���ȡ��BigDecimal����ת����int�����ء�
		return Round.getRound(dou, 0, roundmode).longValue();
	}
	/**
	 * Ҫ������󷵻�BigDecimal����
	 * 
	 * @param dou  �����������
	 * @param scale ���ص�BigDecimal����ı�ȣ�scale��
	 * @param roundmode  ����ģʽ
	 * @return
	 */
	public static BigDecimal getRound(double dou, int scale, int roundmode) {
		//����һ���µ�BigDecimal����paramNumber���ö����ֵ��dou��Сһ����
		BigDecimal paramNumber = new BigDecimal(dou);
		//Ȼ�����paramNumber��setScale�������÷�������һ�� BigDecimal����temp��
		//����ֵ�ı��Ϊ��һ������ָ����ֵ�����Ϊ��С��ʾС�����ֵ�λ��
		//�ڶ�������ָ����paramNumber����temp���������ģʽ������������ȡ�
		return paramNumber.setScale(scale, roundmode);
		//ʵ�ʿ���һ�����ʵ�֣�return new BigDecimal(dou).setScale(0, roundmode);
	}
	public static void main(String[] args) {
		double dou1 = 8.50;
		double dou2 = -9.54;
		System.out.println("����������֣�dou1 = " + dou1 + "; dou2 = " + dou2);
		System.out.println("����Math��ʵ����������Ľ��: \t" + Round.getTraRoundMath(dou1)
				+ "\t" + Round.getTraRoundMath(dou2));
		System.out.println("���������Ľ��: \t" + Round.getTraRound(dou1) + "\t"
				+ Round.getTraRound(dou2));

		System.out.println("Ҫ������󷵻�������");
		//�ӽ�������������ģʽ
		System.out.println("����ģʽROUND_CEILING: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_CEILING) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_CEILING));

		//�ӽ��������ģʽ
		System.out.println("����ģʽROUND_DOWN: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_DOWN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_DOWN));

		//����Զ���������ģʽ
		System.out.println("����ģʽROUND_UP : \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_UP) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_UP));

		//�ӽ�������������ģʽ
		System.out.println("����ģʽROUND_FLOOR: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_FLOOR) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_FLOOR));

		//����ӽ��ġ��������룬����������������ֵľ�����ȣ���ΪROUND_DOWN����ģʽ��
		System.out.println("����ģʽROUND_HALF_DOWN:\t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_DOWN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_DOWN));

		//����ӽ��ġ��������룬����������������ֵľ�����ȣ��������ڵ�ż�����롣
		System.out.println("����ģʽROUND_HALF_EVEN:\t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_EVEN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_EVEN));

		//����ӽ��ġ��������룬����������������ֵľ�����ȣ���ΪROUND_UP����ģʽ��
		System.out.println("����ģʽROUND_HALF_UP : \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_UP) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_UP));

		System.out.println();
		System.out.println("Ҫ������󷵻����ֵ�С�����ֱ���һλ��");
		System.out.println("����ģʽROUND_CEILING: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_CEILING) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_CEILING));
		System.out.println("����ģʽROUND_DOWN: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_DOWN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_DOWN));
		System.out.println("����ģʽROUND_UP : \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_UP) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_UP));
		System.out.println("����ģʽROUND_FLOOR: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_FLOOR) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_FLOOR));
		System.out.println("����ģʽROUND_HALF_DOWN:\t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_DOWN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_DOWN));
		System.out.println("����ģʽROUND_HALF_EVEN:\t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_EVEN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_EVEN));
		System.out.println("����ģʽROUND_HALF_UP : \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_UP) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_UP));
	}
}