package com.zstax.util.常用类.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����û�������ַ����Ƿ���һ����ʽ�Ϸ��ĵ绰���롣
 * �й����ڵ绰����ĸ�ʽ�ǣ�
 * 3λ���ż�8λ�������4λ���ż�7λ���룬�м������һ��-�ţ�����û��
 */
public class CheckPhoneNO {
	/**�绰�����������ʽ*/
	private static String PATTERN_ONE = "^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$";
	//PATTERN_TWO������ʽ�ȼ���PATTERN_ONE
//	private static String PATTERN_TWO = "^(\\d{3}-?\\d{8})|(\\d{4}-?\\d{7})$";

	/**
	 * ʹ��String��������������ʽƥ��ķ���
	 * @param phoneNO	������ĵ绰����
	 * @return
	 */
	public static boolean usingStringRegex(String phoneNO){
		if (phoneNO != null){
			return phoneNO.matches(PATTERN_ONE);
		} else {
			return false;
		}
	}
	/**
	 * ʹ��Pattern��Matcherƥ��
	 * @param phoneNO
	 * @return
	 */
	public static boolean usingPatternRegex(String phoneNO){
		Pattern p = Pattern.compile(PATTERN_ONE);
		//����һ��Matcher
		Matcher m = p.matcher(phoneNO);
		//��ȷƥ��		
		return m.matches();
	}

	public static void main(String[] args) {
		//���ϸ�ʽ�ĺ���
		String phoneNO = "010-88888888";
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? "
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "01088888888";
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "0731-6666666";
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		//�����ϸ�ʽ�ĺ���
		phoneNO = "010-7777777777";
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "0df0-777dg77";
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " ��һ����ʽ�Ϸ��ĵ绰����? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
	}
}
