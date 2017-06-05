package com.zstax.util.常用类.j2se5;
/**
 * �ɱ䳤�Ĳ�����
 * ��ʱ�����Ǵ��뵽�����Ĳ����ĸ����ǲ��̶��ģ�Ϊ�˽��������⣬����һ���������ķ�����
 * 1��  ���أ������ؼ��������������ܵ���������ĸ�������Ȼ�ⲻ��ʲô�ð취��
 * 2��  ��������Ϊһ�����鴫�롣��Ȼ��������ֻ��һ���������ɣ����ǣ�
 * Ϊ�˴���������飬������Ҫ������һ�����飬Ȼ�󽫲���һ��һ���ӵ������С�
 * ���ڣ����ǿ���ʹ�ÿɱ䳤�������������⣬
 * Ҳ����ʹ��...�����������ɿɱ䳤��������Ȼ���ɱ䳤�������������һ��������
 */
public class VarArgs {

	/**
	 * ��ӡ��Ϣ����Ϣ�������������
	 * @param debug	�Ƿ�debugģʽ
	 * @param msgs	����ӡ����Ϣ
	 */
	public static void printMsg(boolean debug, String ... msgs){
		if (debug){
			// ��ӡ��Ϣ�ĳ���
			System.out.println("DEBUG: ����ӡ��Ϣ�ĸ���Ϊ" + msgs.length);
		}
		for (String s : msgs){
			System.out.println(s);
		}
		if (debug){
			// ��ӡ��Ϣ�ĳ���
			System.out.println("DEBUG: ��ӡ��Ϣ����");
		}
	}
	/**
	 * ����printMsg����������һ���������͸�Ϊint
	 * @param debugMode �Ƿ�debugģʽ
	 * @param msgs	����ӡ����Ϣ
	 */
	public static void printMsg(int debugMode, String ... msgs){
		if (debugMode != 0){
			// ��ӡ��Ϣ�ĳ���
			System.out.println("DEBUG: ����ӡ��Ϣ�ĸ���Ϊ" + msgs.length);
		}
		for (String s : msgs){
			System.out.println(s);
		}
		if (debugMode != 0){
			// ��ӡ��Ϣ�ĳ���
			System.out.println("DEBUG: ��ӡ��Ϣ����");
		}
	}
	
	public static void main(String[] args) {
		// ����printMsg(boolean debug, String ... msgs)����
		VarArgs.printMsg(true);
		VarArgs.printMsg(false, "��һ����Ϣ", "���ǵڶ���");
		VarArgs.printMsg(true, "��һ��", "�ڶ���", "���ǵ�����");
		
		// ����printMsg(int debugMode, String ... msgs)����
		VarArgs.printMsg(1, "The first message", "The second message");
	}
}