package com.zstax.util.常用类.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * StringTokenizer��Ҫ�������ݷָ������ָ��ַ�����
 */
public class UsingStringTokenizer {

	/**Ĭ�Ϸָ���*/
	public final static String DELIM = ",";
	
	public static String[] process(String line){
		return process(line, DELIM, false);
	}

	public static String[] process(String line, String delim){
		return process(line, delim, false);
	}
	/**
	 * ��StringTokenizer�ָ��ַ���
	 * @param line	���ָ���ַ���
	 * @param delim	�ָ��
	 * @param returnDelims	�Ƿ񷵻طָ�����Ĭ��Ϊfalse��
	 * @param maxfields �ָ������Ķ���
	 * @return		���ָ����ַ�������
	 */
	public static String[] process(String line, String delim, boolean returnDelims) {

		List results = new ArrayList();
		//�½�һ��StringTokenizer����
		StringTokenizer st = new StringTokenizer(line, delim, returnDelims);
		//ѭ��������ַ����л��зָ����������
		while (st.hasMoreTokens()) {
			//ȡ��һ���ָ�������һ���ָ���֮����ַ���
			String s = st.nextToken();
			//���м���ַ�����ӵ�����б���
			results.add(s);
		}
		return (String[])results.toArray(new String[0]);
	}
	/**
	 * ����ָ���
	 * @param input
	 * @param outputs
	 */
	public static void printResults(String input, String[] outputs) {
		System.out.println("Input: " + input);
		for (int i = 0; i < outputs.length; i++){
			System.out.println("Output " + i + " was: " + outputs[i]);
		}
	}

	public static void main(String[] a) {
		printResults("A|B|C|D", process("A|B|C|D", "|"));
		printResults("A||C|D", process("A||C|D", "|", true));
		printResults("A|||D|E", process("A|||D|E", "|", false));
		printResults("A;bD;|E;FG", process("A;bD;|E;FG", ";"));
		printResults("A;bD;|E;FG;dfxxf;ert", process("A;bD;|E;FG;dfxxf;ert", ";", false));
	}
}
