package com.zstax.util.常用类.basic;

public class Factorial {
	/**
	 * ����n����ֵ
	 */
	public long getFactorial(int n) {
		// ��Ϊ��n����17ʱ��n!��ֵ������long���͵ķ�Χ������ִ�����������޶���n����С�ڵ���17��
		// ��ѧ��û�и����Ľ׳˵ĸ�����n������ڵ���0��
		if ((n < 0) || (n > 17)) {
			System.err.println("n��ֵ��Χ����������[0, 17]�ڣ�");
			return -1;
		} else if (n == 0) {
			// 0!��ֵΪ1
			return 1;
		} else {
			long result = 1;
			for (; n > 0; n--) {
				result *= n;
			}
			return result;
		}
	}
	public static void main(String[] args) {
		Factorial test = new Factorial();
		System.out.println(test.getFactorial(15));//1307674368000
	}
}
