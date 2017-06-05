package com.zstax.util.常用类.thread;

/**
 * �̵߳Ļ��⣬��Ҫչʾͬ���������ͬ������������
 */
public class Synchronized {
	/**
	 * �ʺ���
	 */
	static class Account{
		//������Դ�� Ǯ��
		private double money = 1000.0d;
		/**
		 * ��Ǯ	û��ͬ������
		 * @param fFees
		 */
		public void nonSynDeposit(double fFees){
			System.out.println("Account nonSynDeposit begin! money = " + this.money + "; fFees = " + fFees);
			//��ǮǮ�ȵȴ�300����
			System.out.println("Account nonSynDeposit sleep begin!");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account nonSynDeposit sleep end!");
			this.money = this.money + fFees;
			System.out.println("Account nonSynDeposit end! money = " + this.money);
		}
		/**
		 * ȡǮ	û��ͬ������
		 * @param fFees
		 */
		public void nonSynWithdraw(double fFees){
			System.out.println("Account nonSynWithdraw begin! money = " + this.money + "; fFees = " + fFees);
			//ȡǮʱ�ȵȴ�400����
			System.out.println("Account nonSynWithdraw sleep begin!");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account nonSynWithdraw sleep begin!");
			this.money = this.money - fFees;
			System.out.println("Account nonSynWithdraw end! money = " + this.money);
		}
		/**
		 * ��Ǯ	��ͬ������
		 * @param fFees
		 */
		public synchronized void synDeposit(double fFees){
			System.out.println("Account synDeposit begin! money = " + this.money + "; fFees = " + fFees);
			//��ǮǮ�ȵȴ�300����
			System.out.println("Account synDeposit sleep begin!");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account synDeposit sleep begin!");
			this.money = this.money + fFees;
			System.out.println("Account synDeposit end! money = " + this.money);
		}
		/**
		 * ȡǮ	��ͬ������
		 * @param fFees
		 */
		public synchronized void synWithdraw(double fFees){
			System.out.println("Account synWithdraw begin! money = " + this.money + "; fFees = " + fFees);
			//ȡǮʱ�ȵȴ�400����
			System.out.println("Account synWithdraw sleep begin!");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Account synWithdraw sleep end!");
			this.money = this.money - fFees;
			System.out.println("Account synWithdraw end! money = " + this.money);
		}
	}
	
	static class AccessThread extends Thread{
		//�����ʵ��ʺŶ���
		private Account account = null;
		//�����ʺŵķ���
		private String method = "";

		public AccessThread(Account account, String method){
			this.method = method;
			this.account = account;
		}
		public void run(){
			//�Բ�ͬ�ķ������������ò�ͬ�ķ���
			if (method.equals("nonSynDeposit")){
				account.nonSynDeposit(500.0);
			} else if (method.equals("nonSynWithdraw")){
				account.nonSynWithdraw(200.0);
			} else if (method.equals("synDeposit")){
				account.synDeposit(500.0);
			} else if (method.equals("synWithdraw")){
				account.synWithdraw(200.0);
			}
		}
	}

	public static void main(String[] args) {
		//���������ʺŶ������в�������Ը��ʺ�
		Account account = new Account();
		
		System.out.println("ʹ�÷�ͬ������ʱ��");
		//��ͬ��������Ǯ���߳�
		Thread threadA = new AccessThread(account, "nonSynDeposit");
		//��ͬ������ȡǮ���߳�
		Thread threadB = new AccessThread(account, "nonSynWithdraw");
		threadA.start();
		threadB.start();
		//�ȴ����߳����н���
		try {
			threadA.join();
			threadB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//�������ͬ������
		System.out.println();
		account = new Account();
		System.out.println("ʹ��ͬ������ʱ��");
		threadA = new AccessThread(account, "synDeposit");
		threadB = new AccessThread(account, "synWithdraw");
		threadA.start();
		threadB.start();
	}
}
