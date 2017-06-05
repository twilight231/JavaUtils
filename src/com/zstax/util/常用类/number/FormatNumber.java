package com.zstax.util.常用类.number;

import java.text.DecimalFormat;
/**
 * ��ʽ�����ֵ����
 */
public class FormatNumber {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat();
		double data = 1203.405607809;
		System.out.println("��ʽ��֮ǰ������: " + data);
		// �ڸ�ʽ����ʱ����Զ��������룬����ģʽ�ǣ�
		// ����ӽ��ġ��������룬����������������ֵľ�����ȣ��������ڵ�ż�����롣

		// ģʽ�е�"."��ʾС���ָ���
		// ģʽ�е�"0"��ʾ�����λ�����ַ�������ʾ�ַ�����������ڣ�����ʾ0
		String pattern = "0.0";// ��ʾ��ʽ
		df.applyPattern(pattern);// ����ʽӦ���ڸ�ʽ����
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));// 1203.4
		pattern = "00000.000 kg";// ������ģʽ�����Լ���Ҫ���κ��ַ������絥λ
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));// 01203.406 kg

		// ģʽ�е�"#"��ʾ�����λ�����ַ�������ʾ�ַ�����������ڣ�����ʾ��
		pattern = "##000.000 kg";// ע��#ֻ�ܳ�����ģʽ����ͷ��������0�м�
		// ���� pattern = "##00#.#0"
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));// 1203.406 kg

		// ģʽ�е�"-"��ʾ���Ϊ������Ҫ������ǰ��
		pattern = "-000.000";
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));

		// ģʽ�е�","����������Ӷ��ţ����������
		pattern = "-0,000.0#";
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));

		// ģʽ�е�"E"��ʾ���Ϊָ����"E"֮ǰ���ַ����ǵ����ĸ�ʽ��
		// "E"֮������ַ�����ָ���ĸ�ʽ
		pattern = "0.00E000";
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));

		// ģʽ�е�"%"��ʾ����100����ʾΪ�ٷ�����Ҫ�������
		pattern = "0.00%";
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));

		// ģʽ�е�"\u2030"��ʾ����1000����ʾΪǧ������Ҫ�������
		pattern = "0.00\u2030";
		df.applyPattern(pattern);
		System.out.println("����pattern: " + pattern + "��ʽ��֮��: "
				+ df.format(data));
	}
}
