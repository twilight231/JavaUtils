package com.zstax.util.常用类.mutimedia.sound;

/**
 * ����������
 */
public class Beep {

	public static void main(String[] args) {
		// ���ն˳����У�'\u0007'��ʾ����
		System.out.println("Beep!" + '\u0007');
		
		// ��GUI�����У�Toolkit��beep��������
		java.awt.Toolkit.getDefaultToolkit( ).beep( );
	}
}
