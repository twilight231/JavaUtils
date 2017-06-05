package com.zstax.util.常用类.string;

/**
 * ������ݺ��������������,��ʮ��λ���ֱ������һλ����У�������.����˳�������������Ϊ:
 * ��λ���ֵ�ַ��,��λ���ֳ���������,��λ����˳�����һλ����У���롣
 * 1����ַ�룺��ʾ�������ס���������أ��С��졢�����������������룬�� GB/T 2260 �Ĺ涨ִ�С� 
 * 2�����������룺��ʾ�������������ꡢ�¡��գ��� * GB/T 7408 �Ĺ涨ִ�С��ꡢ�¡��մ���֮�䲻�÷ָ����� 
 * ����ĳ�˳�������Ϊ 1966��10��26�գ������������Ϊ 19661026��
 * 3��˳���룺��ʾ��ͬһ��ַ������ʶ������Χ�ڣ�
 * ��ͬ�ꡢͬ�¡�ͬ�ճ������˱ඨ��˳��ţ�˳�����������������ԣ�ż��ǧ�����Ů�ԡ� 
 * 4��У���룺У�������ISO 7064��1983��MOD 11-2 У����ϵͳ�� 
 * ��1��ʮ��λ���ֱ������Ȩ��͹�ʽ 
 * S = Sum(Ai * Wi), i = * 0, ... , 16 ���ȶ�ǰ17λ���ֵ�Ȩ��� 
 * Ai:��ʾ��iλ���ϵ����֤��������ֵ 
 * Wi:��ʾ��iλ���ϵļ�Ȩ���� 
 * Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
 * ��2������ģ Y = mod(S, 11) 
 * ��3��ͨ��ģ�õ���Ӧ��У���� 
 * Y: 0 1 2 3 4 5 6 7 8 9 10 
 * У����: 1 0 X 9 8 7 6 5 4 3 2
 */
public class IDCard {
	// ��Ȩ����
	private static final int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6,
			3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// У����
	private static final int[] checkDigit = new int[] { 1, 0, 'X', 9, 8, 7, 6,
			5, 4, 3, 2 };

	public IDCard() {
	}
	/**
	 * ��֤���֤�Ƿ���ϸ�ʽ
	 * @param idcard
	 * @return
	 */
	public boolean Verify(String idcard) {
		if (idcard.length() == 15) {
			idcard = this.update2eighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		//��ȡ�������֤�ϵ����һλ������У����
		String checkDigit = idcard.substring(17, 18);
		//�Ƚϻ�ȡ��У�����뱾�������ɵ�У�����Ƿ����
		if (checkDigit.equals(this.getCheckDigit(idcard))) {
			return true;
		}
		return false;
	}

	/**
	 * ����18λ���֤��У����
	 * @param eighteenCardID	18λ���֤
	 * @return
	 */
	private String getCheckDigit(String eighteenCardID) {
		int remaining = 0;
		if (eighteenCardID.length() == 18) {
			eighteenCardID = eighteenCardID.substring(0, 17);
		}

		if (eighteenCardID.length() == 17) {
			int sum = 0;
			int[] a = new int[17];
			//�ȶ�ǰ17λ���ֵ�Ȩ���
			for (int i = 0; i < 17; i++) {
				String k = eighteenCardID.substring(i, i + 1);
				a[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + weight[i] * a[i];
			}
			//����11ȡģ
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(checkDigit[remaining]);
	}

	/**
	 * ��15λ���֤������18λ���֤����
	 * @param fifteenCardID
	 * @return
	 */
	private String update2eighteen(String fifteenCardID) {
		//15λ���֤�ϵ������е����û��19��Ҫ����
		String eighteenCardID = fifteenCardID.substring(0, 6);
		eighteenCardID = eighteenCardID + "19";
		eighteenCardID = eighteenCardID + fifteenCardID.substring(6, 15);
		eighteenCardID = eighteenCardID + this.getCheckDigit(eighteenCardID);
		return eighteenCardID;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IDCard test = new IDCard();
		String idCardStr = "110105194912310025";
		System.out.println("���֤" + idCardStr + "��֤�ϸ� " 
				+ test.Verify(idCardStr));
		idCardStr = "440524188001010014";
		System.out.println("���֤" + idCardStr + "��֤�ϸ� "  
				+ test.Verify(idCardStr));
	}
}
