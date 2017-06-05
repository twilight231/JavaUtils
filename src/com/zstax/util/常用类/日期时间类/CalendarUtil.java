package com.zstax.util.常用类.日期时间类;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {

	public static void main(String args[]) {

		System.out.println("First day of week is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getFirstDateByWeek(new Date())));

		System.out.println("Last day of week is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getLastDateByWeek(new Date())));
		
		System.out.println("First day of month is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getFirstDateByMonth(new Date())));
		
		System.out.println("Last day of month is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getLastDateByMonth(new Date())));
	}

	/**
	 * ����������ڵĵ�һ��
	 */
	public static Date getFirstDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // ����һ
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}

	/**
	 * ����������ڵ����һ��
	 */
	public static Date getLastDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // ����һ
		int last_day_of_week = first_day_of_week + 6; // ������
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}

	/**
	 * ��������·ݵ����һ��
	 * @param ��ǰ�·����ڵ�ʱ��
	 * @return �·ݵ����һ��
	 */
	public static Date getLastDateByMonth(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		now.set(Calendar.HOUR, 11);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * ��������·ݵĵ�һ��
	 * @param ��ǰ�·����ڵ�ʱ�� 
	 * @return �·ݵĵ�һ��
	 */
	public static Date getFirstDateByMonth(Date date) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, 0);
		now.set(Calendar.HOUR, 12);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}
}
