package com.zstax.util.常用类.arrayset;

import java.util.Arrays;
/**
 * ��ָ����Χ�ڵ�����
 */
public class PrimeNumber {
	/**
	 * ��ʾrange��Χ�ڵ�����
	 * @param range
	 */
	public void showPrimeNumber(int range){
		boolean[] primes = this.sieve(range);
		int number = 0;
		if (primes != null){
			int size = primes.length;
			System.out.println("��Χ��" + range + "�ڵ�����������:");
			for (int i=1; i<size; i++){
				if (primes[i]){
					System.out.print(i + "  ");
					//ÿ���10����������
					//number�ȼ�1���ٸ�10��ģ���㣬�������Ϊ0������
					if (++number % 10 == 0){
						System.out.println();
					}
				}
			}
			System.out.println();
		} 
		System.out.println("һ����" + number + "��");
	}
	/**
	 * ɸѡ��������
	 * @param range
	 * @return
	 */
	private boolean[] sieve(int range){
		if (range <= 0){
			System.out.println("�������ķ�Χrange�������0��");
			return null;
		} 
		//��һ�����������ʾ�Ƿ�Ϊ����������±�ֵΪ��������ô���±�ֵ��Ӧ������Ԫ�ص�ֵΪtrue��
		//��2��������isPrime[2] = true
		//��Ϊ�������±��Ǵ�0��ʼ�ģ����������½��������СΪrange+1
		boolean[] isPrime = new boolean[range + 1];
		//1��������
		isPrime[1] = false;
		//��Arrays��fill�����������±��2��range+1֮���Ԫ�ص�ֵ����Ϊtrue
		Arrays.fill(isPrime, 2, range+1, true);
		//����һ�����ȼ������汻ע�͵Ĵ���
//		for (int i=2; i< range+1; i++){
//			isPrime[i] = true;
//		}
		//Math��sqrt���������󿪷�
		int n = (int)Math.sqrt(range);
		for (int i=1; i<=n; i++){
			if (isPrime[i]){
				//���i����������ôi�ı�����������
				for (int j=2 * i; j<=range; j+=i){
					isPrime[j] = false;
				}
			}
		}
		return isPrime;
	}   
	public static void main(String[] args) {
		int range = 200;
		PrimeNumber test = new PrimeNumber();
		test.showPrimeNumber(range);
	}
}
