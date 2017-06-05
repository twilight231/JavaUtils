package com.zstax.util.常用类.string;

/**
 * ʹ���ַ�����String
 */
public class UsingString {
	/**
	 * �������ַ���
	 * @param str
	 */
	public static void testFindStr(String str) {
		// indexOf�������ַ������ַ����е����ȳ��ֵ�λ�ã���������ڣ����ظ�����
		System.out.println("str.indexOf(\"is\") = " + str.indexOf("is"));
		// ���Ը�indexOf�������ò�����ָ��ƥ�����ʼλ��
		System.out.println("str.indexOf(\"is\", 4) = " + str.indexOf("is", 4));
		// lastIndexOf�������ַ������ַ����е������ֵ�λ�ã���������ڣ����ظ�����
		System.out
				.println("str.lastIndexOf(\"is\") = " + str.lastIndexOf("is"));
		// ���Ը�lastIndexOf�������ò�����ָ��ƥ��Ľ���λ��
		System.out.println("str.lastIndexOf(\"is\", 1) = "
				+ str.lastIndexOf("is", 1));
	}
	/**
	 * ��ȡ�ַ���
	 * @param str
	 */
	public static void testSubStr(String str) {
		// substring������ȡ�ַ���������ָ����ȡ����ʼλ�ú���ֹλ�ã�
		// Ĭ����ֹλ��Ϊ�ַ���ĩβ
		System.out.println("str.substring(2) = " + str.substring(2));
		System.out.println("str.substring(2, 9) = " + str.substring(2, 9));
	}
	/**
	 * �滻�ַ���
	 * @param str
	 */
	public static void testReplaceStr(String str) {
		// replace�������ַ����е�ĳ���ַ��滻����һ���ַ�
		System.out.println("str.replace('i', 'I') = " + str.replace('i', 'I'));
		// replaceAll�������ַ����е�ĳ�����ַ����滻����һ���ַ���
		System.out.println("str.replaceAll(\"is\", \"IS\") = "
				+ str.replaceAll("is", "IS"));
		// replaceFirst�������ַ����е�ĳ�����ַ����ĵ�һ���滻����һ���ַ���
		System.out.println("str.replaceFirst(\"is\", \"IS\") = "
				+ str.replaceFirst("is", "IS"));
	}
	/**
	 * ת����Сд
	 * @param str
	 */
	public static void testToUpperOrLower(String str) {
		// toUpperCase�������ַ���ȫ����ɴ�д��ʽ
		System.out.println("str.toUpperCase() = " + str.toUpperCase());
		// toLowerCase�������ַ���ȫ�����Сд��ʽ
		System.out.println("str.toLowerCase() = " + str.toLowerCase());
	}
	/**
	 * ��ȡ�ַ�����ĳ��λ���ϵ��ַ�
	 * @param str
	 */
	public static void testCharAt(String str) {
		// charAt���������ַ�����ĳ��λ���ϵ��ַ�
		System.out.println("str.charAt(2) = " + str.charAt(2));
		// ���ַ���ת�����ַ����飬���鳤��Ϊ�ַ����ĳ���
		System.out.println(str.toCharArray());
	}
	/**
	 * �Ƚ��ַ����Ĵ�С
	 * @param str
	 */
	public static void testCompareStr(String str) {
		// compareTo�����Ƚ������ַ����Ĵ�С
		// �ȽϹ������ȱȽϵ�һ���ַ��������ַ�����ȣ�����ַ������Ŀ���ַ�С���򷵻ظ�����
		// ���������Ƚϵڶ����ַ������򷵻�������
		System.out.println("str.compareTo(\"I am in Beijing\") = "
				+ str.compareTo("I am in Beijing"));
		// compareToIgnoreCase�ڱȽ�ʱ�����ַ����Ĵ�Сд����Ϊͬһ�ַ��Ĵ�д��Сд����ȵ�
		System.out.println("str.compareToIgnoreCase(\"I am in Beijing\") = "
				+ str.compareToIgnoreCase("I am in Beijing"));
	}
	/**
	 * �Ƚ��ַ����Ƿ����
	 * @param str
	 */
	public static void testEqualsStr(String str) {
		// equals�����Ƚ��ַ�����ֵ������
		System.out.println("str.equals(\"I am in Beijing\") = "
				+ str.equals("I am in Beijing"));
		// equalsIgnoreCase�����Ƚ��ַ�����ֵʱ��ȥ�ִ�Сд
		System.out.println("str.equalsIgnoreCase(\"I am in Beijing\") = "
				+ str.equalsIgnoreCase("I am in Beijing"));
		// startsWith�����ж��ַ����Ƿ���ĳ�����ַ�����ʼ
		System.out.println("str.startsWith(\"Th\") = " + str.startsWith("Th"));
		// endsWith�����ж��ַ����Ƿ���ĳ�����ַ�������
		System.out.println("str.endsWith(\"t!\") = " + str.endsWith("t!"));
	}
	
	/**
	 * String��Ҳ���Ը���������ʽ�����ַ�����
	 * �����ַ���ƥ�䡢�滻�ͷָ�
	 */
	public static void testRegex(){
		String str = "aab        aaa  bb    ab";
		//��������ʽ��ʾ����������Ӣ����ĸ���߿ո�
		String pattern1 = "^[a-zA-Z| ]*$";
		System.out.println("��������ʽƥ��ɹ�? " + str.matches(pattern1));
		
		//�滻�� ���ַ����е����������ո��滻��һ���ո�
		System.out.println(str.replaceAll("\\s{2,}", " "));
		//���ַ����е�һ�������Ŀո��滻��һ���ո�
		System.out.println(str.replaceFirst("\\s{2,}", " "));
		
		//�ָ��ַ��������ո�ָ��������Ŀո���һ���ո�
		String[] ss = str.split("\\s{1,}");
		System.out.println("��������ʽ���ո�ָ�:");
		for (int i=0; i<ss.length; i++){
			System.out.println(ss[i]);
		}
		//���Ʒָ�������Ĵ�С
		System.out.println("��������ʽ���ո�ָָ�����ָ����Ϊ3:");
		ss = str.split("\\s{1,}", 3);
		for (int i=0; i<ss.length; i++){
			System.out.println(ss[i]);
		}
	}

	public static void main(String[] args) {
		String str = "This is a String object!";
		System.out.println("str��ֵ: " + str);
		
		UsingString.testFindStr(str);
		System.out.println();
		UsingString.testSubStr(str);
		System.out.println();
		UsingString.testReplaceStr(str);
		System.out.println();
		UsingString.testToUpperOrLower(str);
		System.out.println();
		UsingString.testCharAt(str);
		System.out.println();
		UsingString.testCompareStr(str);
		System.out.println();
		UsingString.testEqualsStr(str);
		System.out.println();
		UsingString.testRegex();
	}
}
