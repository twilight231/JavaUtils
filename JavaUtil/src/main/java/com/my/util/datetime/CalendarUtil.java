package com.my.util.datetime;

import java.text.ParseException;
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
	 * 获得所在星期的第一天
	 */
	public static Date getFirstDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}

	/**
	 * 获得所在星期的最后一天
	 */
	public static Date getLastDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		int last_day_of_week = first_day_of_week + 6; // 星期日
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}

	/**
	 * 获得所在月份的最后一天
	 * @param 当前月份所在的时间
	 * @return 月份的最后一天
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
	 * 获得所在月份的第一天
	 * @param 当前月份所在的时间 
	 * @return 月份的第一天
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

	public static int getBetweenDate(String oldTime,String nowTime) throws ParseException {
		//把传入的日期中的"-"符号去掉放入一个字符串数组strdata[]中
		String[] strOldData = oldTime.split("-");
		//循环把数组中的值保存为一个字符串对象
		oldTime = strOldData[0];
		for(int i=1;i<strOldData.length;i++){
			oldTime = oldTime+strOldData[i];
		}
		//把传入的日期中的"-"符号去掉放入一个字符串数组strdata[]中
		String[] strNowData = nowTime.split("-");
		//循环把数组中的值保存为一个字符串对象
		nowTime = strNowData[0];
		for(int i=1;i<strNowData.length;i++){
			nowTime = nowTime+strNowData[i];
		}

		//设定日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//把之前的字符串对象转为设定好格式的日期对象
		Date date1 =  format.parse(oldTime);

		Date date2 = format.parse(nowTime);
		//把传入的日期跟当前系统日期相减，得出2个日期之间相差天数
		int differ = Math.abs((int)((date1.getTime()-date2.getTime())/(24*60*60*1000)));
		//返回相差天数
		return differ;
	}
}
