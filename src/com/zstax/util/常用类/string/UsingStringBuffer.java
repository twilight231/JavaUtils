package com.zstax.util.常用类.string;
/**
 * ʹ��StringBuffer������ַ���
 */
public class UsingStringBuffer {
	/**
	 * ����ƥ���ַ���
	 */
	public static void testFindStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// indexOf�������ַ������ַ����е����ȳ��ֵ�λ�ã���������ڣ����ظ�����
		System.out.println("sb.indexOf(\"is\") = " + sb.indexOf("is"));
		// ���Ը�indexOf�������ò�����ָ��ƥ�����ʼλ��
		System.out.println("sb.indexOf(\"is\", 4) = " + sb.indexOf("is", 4));
		// lastIndexOf�������ַ������ַ����е������ֵ�λ�ã���������ڣ����ظ�����
		System.out
				.println("sb.lastIndexOf(\"is\") = " + sb.lastIndexOf("is"));
		// ���Ը�lastIndexOf�������ò�����ָ��ƥ��Ľ���λ��
		System.out.println("sb.lastIndexOf(\"is\", 1) = "
				+ sb.lastIndexOf("is", 1));
	}
	/**
	 * ��ȡ�ַ���
	 */
	public static void testSubStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// substring������ȡ�ַ���������ָ����ȡ����ʼλ�ú���ֹλ�ã�
		// Ĭ����ֹλ��Ϊ�ַ���ĩβ
		System.out.println("sb.substring(2) = " + sb.substring(2));
		System.out.println("sb.substring(2, 9) = " + sb.substring(2, 9));
	}
	/**
	 * ��ȡ�ַ�����ĳ��λ���ϵ��ַ�
	 */
	public static void testCharAt() {
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// charAt���������ַ�����ĳ��λ���ϵ��ַ�
		System.out.println("sb.charAt(2) = " + sb.charAt(2));
	}
	/**
	 * ��Ӹ������͵����ݵ��ַ���β��
	 */
	public static void testAppend(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//����ַ������ַ����鵽StringBufferĩβ
		sb.append('I');
		sb.append(new char[]{' ', 'a','m'});
		System.out.println("Append char: " + sb.toString());
		//����ַ�����StringBufferĩβ
		sb.append(" in ��Beijing.");
		System.out.println("Append String: " + sb.toString());
		//��ӻ�������
		sb.append(15);
		sb.append(1.23f);
		sb.append(3.4d);
		sb.append(899L);
		System.out.println("Append number: " + sb.toString());
		//��Ӳ���ֵ
		sb.append(false);
		sb.append(true);
		System.out.println("Append boolean: " + sb.toString());
	}
	/**
	 * ɾ���ַ����е����� 
	 */
	public static void testDelete(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//ɾ��ָ��λ�õ��ַ�
		sb.deleteCharAt(1);
		sb.deleteCharAt(2);
		System.out.println("Delete char: " + sb.toString());
		//ɾ��ָ����Χ���ַ���
		sb.delete(1, 5);
		System.out.println("sb.delete(0, 5) =  " + sb.toString());
	}
	/**
	 * ���ַ����в���������͵�����
	 */
	public static void testInsert(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//�ܹ���ָ��λ�ò����ַ����ַ����顢�ַ����Լ��������ֺͲ���ֵ
		sb.insert(2, 'W');
		sb.insert(3, new char[]{'A', 'B', 'C'});
		sb.insert(8, "abc");
		sb.insert(2, 3);
		sb.insert(3, 2.3f);
		sb.insert(6, 3.75d);
		sb.insert(5, 9843L);
		sb.insert(2, true);
		System.out.println("testInsert: " + sb.toString());
	}
	/**
	 * �滻�ַ����е�ĳЩ�ַ�
	 */
	public static void testReplace(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//���ַ�����ĳ���ַ��滻����һ���ַ���
		sb.replace(3, 8, "replace");
		System.out.println("testReplace: " + sb.toString());
	}
	/**
	 * ���ַ�������
	 */
	public static void reverseStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//reverse�������ַ�������
		System.out.println(sb.reverse());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		System.out.println("ԭʼStringBuffer: " + sb.toString());
		
		UsingStringBuffer.testFindStr();
		UsingStringBuffer.testSubStr();
		UsingStringBuffer.testCharAt();
		UsingStringBuffer.testAppend();
		UsingStringBuffer.testDelete();
		UsingStringBuffer.testInsert();
		UsingStringBuffer.testReplace();
		UsingStringBuffer.reverseStr();
	}

}
