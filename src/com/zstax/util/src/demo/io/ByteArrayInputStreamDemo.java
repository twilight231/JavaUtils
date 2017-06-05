package com.zstax.util.src.demo.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
/*
 * ByteArrayInputStream��ByteArrayOutputStream����ʾ���ֽ�����������루�����
 * �������ʵ���Ƕ�һ���ֽ�������в�����������ֽ����鿴��һ��������
 * �رշ�����һ���շ������رպ�Ӱ����������
 * ���Խ����鶨λ��ָ��λ�ÿ�ʼ��/д�����Խ������ͷ��ʼ��/д�����Բ鿴���黹�м����ֽڿ���
 * ������ĳ��λ������ǣ����Է��ص����λ�ý��ж�/д
 */
public class ByteArrayInputStreamDemo {
	public static void main(String[] args) {
		// �������������������Ѿ��������ֽڣ�
		byte[] inputBuff = new byte[] { 1, 2, 3, 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] result = new byte[20];
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBuff);
		// �����������ֽڶ���result���鲢���result
		inputStream.read(result, 0, 20);
		System.out.println(Arrays.toString(result));
		// ��result����д�������
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		outStream.write(result, 0, 20);
		System.out.println(Arrays.toString(outStream.toByteArray()));
	}

}
