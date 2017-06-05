package com.zstax.util.常用类.j2se5;

import java.util.Date;

/**
 * ʹ��printf���
 */
public class Printf {

	public static void main(String[] args) {
		/*** ����ַ��� ***/
		// %s��ʾ����ַ�����Ҳ���ǽ�������ַ����滻ģʽ�е�%s
		System.out.printf("%s", new Integer(1212));
		// %n��ʾ����
		System.out.printf("%s%n", "end line");
		// ������֧�ֶ������
		System.out.printf("%s = %s%n", "Name", "Zhangsan");
		// %S���ַ����Դ�д��ʽ���
		System.out.printf("%S = %s%n", "Name", "Zhangsan");
		// ֧�ֶ������ʱ��������%s֮����������ţ�1$��ʾ��һ���ַ�����3$��ʾ��3���ַ���
		System.out.printf("%1$s = %3$s %2$s%n", "Name", "san", "Zhang");
		
		/*** ���boolean���� ***/
		System.out.printf("true = %b; false = ", true);
		System.out.printf("%b%n", false);

		/*** �����������***/
		Integer iObj = 342;
		// %d��ʾ��������ʽ��Ϊ10��������
		System.out.printf("%d; %d; %d%n", -500, 2343L, iObj);
		// %o��ʾ��������ʽ��Ϊ8��������
		System.out.printf("%o; %o; %o%n", -500, 2343L, iObj);
		// %x��ʾ��������ʽ��Ϊ16��������
		System.out.printf("%x; %x; %x%n", -500, 2343L, iObj);
		// %X��ʾ��������ʽ��Ϊ16����������������ĸ��ɴ�д��ʽ
		System.out.printf("%X; %X; %X%n", -500, 2343L, iObj);
		
		/*** �����������***/
		Double dObj = 45.6d;
		// %e��ʾ�Կ�ѧ���������������
		System.out.printf("%e; %e; %e%n", -756.403f, 7464.232641d, dObj);
		// %E��ʾ�Կ�ѧ���������������������Ϊ��д��ʽ		
		System.out.printf("%E; %E; %E%n", -756.403f, 7464.232641d, dObj);
		// %f��ʾ��ʮ���Ƹ�ʽ�����������
		System.out.printf("%f; %f; %f%n", -756.403f, 7464.232641d, dObj);
		// ����������С������λ��
		System.out.printf("%.1f; %.3f; %f%n", -756.403f, 7464.232641d, dObj);
		
		/*** �����������***/
		// %t��ʾ��ʽ������ʱ�����ͣ�%T��ʱ�����ڵĴ�д��ʽ����%t֮�����ض�����ĸ��ʾ��ͬ�������ʽ
		Date date = new Date();
		long dataL = date.getTime();
		// ��ʽ��������
		// %t֮����y��ʾ������ڵ���ݣ�2λ�����꣬��99��
		// %t֮����m��ʾ������ڵ��·ݣ�%t֮����d��ʾ������ڵ��պ�
		System.out.printf("%1$ty-%1$tm-%1$td; %2$ty-%2$tm-%2$td%n", date, dataL);
		// %t֮����Y��ʾ������ڵ���ݣ�4λ�����꣩��
		// %t֮����B��ʾ������ڵ��·ݵ��������� %t֮����b��ʾ������ڵ��·ݵļ��
		System.out.printf("%1$tY-%1$tB-%1$td; %2$tY-%2$tb-%2$td%n", date, dataL);
		
		// �����ǳ������������
		// %t֮����D��ʾ�� "%tm/%td/%ty"��ʽ������ 
		System.out.printf("%1$tD%n", date);
		//%t֮����F��ʾ��"%tY-%tm-%td"��ʽ������ 
		System.out.printf("%1$tF%n", date);
		
		/*** ���ʱ������***/
		// ���ʱ����
		// %t֮����H��ʾ���ʱ���ʱ��24���ƣ���%t֮����I��ʾ���ʱ���ʱ��12���ƣ���
		// %t֮����M��ʾ���ʱ��ķ֣�%t֮����S��ʾ���ʱ�����
		System.out.printf("%1$tH:%1$tM:%1$tS; %2$tI:%2$tM:%2$tS%n", date, dataL);
		// %t֮����L��ʾ���ʱ������еĺ���
		System.out.printf("%1$tH:%1$tM:%1$tS %1$tL%n", date);
		// %t֮��p��ʾ���ʱ��������������Ϣ
		System.out.printf("%1$tH:%1$tM:%1$tS %1$tL %1$tp%n", date);
		
		// �����ǳ�����ʱ�����
		// %t֮����R��ʾ��"%tH:%tM"��ʽ��ʱ��
		System.out.printf("%1$tR%n", date);
		// %t֮����T��ʾ��"%tH:%tM:%tS"��ʽ��ʱ�� 
		System.out.printf("%1$tT%n", date);
		// %t֮����r��ʾ��"%tI:%tM:%tS %Tp"��ʽ��ʱ�� 
		System.out.printf("%1$tr%n", date);
		
		/*** �������***/
		// %t֮����A��ʾ�õ����ڼ���ȫ��
		System.out.printf("%1$tF %1$tA%n", date);
		// %t֮����a��ʾ�õ����ڼ��ļ��
		System.out.printf("%1$tF %1$ta%n", date);
		
		// ���ʱ�����ڵ�������Ϣ
		System.out.printf("%1$tc%n", date);
	}
}