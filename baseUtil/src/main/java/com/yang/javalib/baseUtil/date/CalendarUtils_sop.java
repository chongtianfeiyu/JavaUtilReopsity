package com.yang.javalib.baseUtil.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
 

/**
 * @author mohen
 * 
 */

public class CalendarUtils_sop {

	private static final Logger logger = Logger.getLogger(CalendarUtils_sop.class);

	/**
	 * 得到本周第一天日期
	 * 
	 * @return
	 */
	public String getThisWeekFirstDayStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();
		System.out.println(cld.get(Calendar.DAY_OF_WEEK));
		cld.add(Calendar.DATE, 1 - cld.get(Calendar.DAY_OF_WEEK));
		//cld.set(Calendar.HOUR, 0);
		cld.set(Calendar.HOUR_OF_DAY, 0);  //modify by jinson
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		return formatter.format(cld.getTime());
	}

	/**
	 * 得到今天时间字符串
	 * 
	 * @return
	 */
	public String getNowDayStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();
		return formatter.format(cld.getTime());
	}

	/**
	 * 得到今天时间字符串
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getNowDayStr2(int m) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();
		Date date = cld.getTime();
		int h = date.getHours();
		int hm = m > h ? 0 : (h - m);
		cld.set(date.getYear() + 1900, date.getMonth(), date.getDate(), hm, date.getMinutes(), date.getSeconds());
		return formatter.format(cld.getTime());
	}

	/**
	 * 返回本月采样数据表名后缀
	 * 
	 * @return
	 */
	public String getYearMonth() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);
		return str;
	}

	/**
	 * 将日期转换为日期表名
	 * 
	 * @param date
	 * @return
	 */
	public String convertToYearMonth(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);
		return str;
	}

	/**
	 * 将日期转换为日期表名
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String convertMonthToYearMonth(Integer month) {
		Date date = new Date();
		date.setMonth(month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);
		return str;
	}

	/**
	 * 得到时间偏移M分钟的时间
	 * 
	 * @param Date
	 * @param m
	 * @param flag
	 * @return
	 */
	public Date convertDateSplitMinuter(Date Date2, Integer m, Boolean flag) {
		m++;
		Long ntime = Date2.getTime() / 1000;
		Long time = (flag ? (ntime + 60 * m) : (ntime - 60 * m));
		Date2.setTime(time * 1000);
		return Date2;
	}

	/**
	 * 返回当天日期
	 * 
	 * @return
	 */
	public String getYearMonthDay() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * 得到时间索引（在一天中的索引）
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int dayIndex(Date date) {
		int h = date.getHours();
		int m = date.getMinutes();
		int count = h * 60 + m;
		int index = count / 30;
		return index;
	}

	/**
	 * 得到时间索引（在一周中的索引）
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int countIndex(Date date) {
		int week = date.getDay();
		int h = date.getHours();
		int m = date.getMinutes();
		int count = h * 60 + m;
		int index = week * 48 + count / 30;
		return index;
	}

	/**
	 * 得到时间索引（在一个月中的索引）
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int monthIndex(Date date) {
		int day = date.getDate() - 1;
		int h = date.getHours();
		int count = day * 24 + h;
		return count + 1;
	}
	
	/**
	 * 返回本月到目前位置的时间分段 以一个小时为一段
	 * @param months 从0开始
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List allMonth(Integer months) {
		List list = new ArrayList();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar endpoint = Calendar.getInstance();//结束时刻
		if(endpoint.get(Calendar.MONTH) == months){
			//本月的结束时刻为当前时刻
		}else{
			//其它月份则为当月的最后时刻(即月末一天的23点)
			endpoint.set(Calendar.MONTH, months);
			endpoint.set(Calendar.DATE, endpoint.getActualMaximum(Calendar.DAY_OF_MONTH));
			endpoint.set(Calendar.HOUR_OF_DAY, 23);
		}
		
		//开始计算这些时间点
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, endpoint.get(Calendar.MONTH));
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0); //modify by jinson
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		while(calendar.before(endpoint)){
			list.add(formatter.format(calendar.getTime()));
			calendar.add(Calendar.HOUR, 1);//后推1小时
		}
		
		return list;
	}

	/**
	 * 返回一周到目前位置的时间分段 以半个小时为一段
	 * 
	 * @return
	 */
	@SuppressWarnings( { "finally", "deprecation", "unchecked" })
	public List allWeek() {
		SimpleDateFormat formatter2 = new SimpleDateFormat("E HH:mm:ss");
		SimpleDateFormat formatter3 = new SimpleDateFormat("E");
		SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<String>();
		try {

			Date date1 = formatter4.parse("2008-08-01 00:00:00");
			for (int i = 0; i <= 336; i++) {
				if (date1.getHours() == 0 && date1.getMinutes() == 0 && date1.getSeconds() == 0)
					list.add(String.valueOf(formatter3.format(date1)));
				else
					list.add(String.valueOf(formatter2.format(date1)));
				long Time = (date1.getTime() / 1000) + 60 * 30;
				date1.setTime(Time * 1000);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return list;

	}

	/**
	 * 一天的时间索引
	 * 
	 * @return
	 */
	public List<String> allDay() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		List<String> list = new ArrayList<String>();
		try {

			Date date1 = formatter.parse("2008-08-01 00:00:00");
			for (int i = 0; i < 48; i++) {
				list.add(String.valueOf(formatter.format(date1)));
				long Time = (date1.getTime() / 1000) + 60 * 30;
				date1.setTime(Time * 1000);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return list;
	}

	/**
	 * 得到上个星期天的日期（一周的开始时间）
	 * 
	 * @return String
	 */
	public String getLastDateSunday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);  //modify by jinson
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		return formatter.format(cal.getTime());
	}

	/**
	 * 得到上个星期六日期（一周的结束时间）
	 * 
	 * @return
	 */
	public String getLastDateSaturday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		//cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		return formatter.format(cal.getTime());
	}
	
	public Date getDate(String dateStr){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return formatter.parse(dateStr);
		}catch(Exception e){
			CalendarUtils_sop.logger.error("转换日期失败", e);
			return null;
		}
	}

	public static void main(String[] args) {
		CalendarUtils_sop util = new CalendarUtils_sop();
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());*/
		String dateStr = util.getYearMonthDay() + " 00:00:00";
		System.out.println(util.getThisWeekFirstDayStr());
//		try{
//			Date dateTime = DateUtil.getdate1(dateStr);
//			Date date20110519 = util.getDate("2011-5-19");
//			if(dateTime.equals(date20110519))
//				System.out.println("ok");
//		}catch(Exception e){
//			
//		}
		
		
		
		
		//System.out.println(util.getYearMonthDay());
		//System.out.println(util.getDate("2011-5-19"));
	}
	
	public static String fmtNextWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY-curDay+Calendar.SATURDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		String weekStr = (week>9)? String.valueOf(week) : "0"+week;
		formatStr = cal.get(Calendar.YEAR)+weekStr;
		return formatStr;
	}
	
	public static String fmtWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		if(curDay != Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY-curDay);
		}
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		String weekStr = (week>9)? String.valueOf(week) : "0"+week;
		formatStr = cal.get(Calendar.YEAR)+weekStr;
		//System.out.println(formatStr);
		return formatStr;
	}
	
}
