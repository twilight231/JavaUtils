package com.zstax.util.常用类.j2se5;

// ע�⾲̬�����д��: import static
import static java.lang.Math.max;//������Math��max����
import static java.lang.Math.min;//������Math��min����
import static java.lang.Integer.*;//������Integer�����о�̬����������
/**
 * ��̬���롣��ʹ��������Ĺ�����̬������������ʱ������ָ��������
 * ֻ��Ҫ����ľ�̬���������Է���import�����м��ɡ�
 */
public class StaticImport {
	public static void main(String[] args) {
		// ͨ����̬����ʹ��Math�ľ�̬����
		System.out.println(max(5,10));
		System.out.println(min(5,10));
		// ͨ����̬����ʹ��Integer�ľ�̬����
		System.out.println(parseInt("55544"));
		System.out.println(toBinaryString(2354));
		// ͨ����̬����ʹ��Integer�ľ�̬����
		System.out.println(MAX_VALUE);
		System.out.println(MIN_VALUE);
	}
}