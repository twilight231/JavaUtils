package com.zstax.util.src.demo.others;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {
	public static void main(String[] args) {
		Date d = new Date();
		//�����ʽ��dow mon dd hh:mm:ss zzz yyyy
		//��ʾ      ������  ��    ����  ʱ   ��   ��    ʱ�� ��
		System.out.println(d);
		//Format ��һ�����ڸ�ʽ�����Ի������е���Ϣ�������ڡ���Ϣ�����֣��ĳ�����ࡣ
		Format format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		System.out.println(format.format(d));
	}
}
