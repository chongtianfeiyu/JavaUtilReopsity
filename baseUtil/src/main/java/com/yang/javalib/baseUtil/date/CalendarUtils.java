package com.yang.javalib.baseUtil.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class CalendarUtils {

	private static Logger log = Logger.getLogger(CalendarUtils.class);
	public static final String SIMPLE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String getThisWeekFirstDayStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();
		cld.add(5, 1 - cld.get(7));
		cld.set(10, 0);
		cld.set(12, 0);
		cld.set(13, 0);

		return formatter.format(cld.getTime());
	}

	public static String getNowDayStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();

		return formatter.format(cld.getTime());
	}

	public static String getNowDayStr2(int m) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cld = Calendar.getInstance();
		Date date = cld.getTime();

		int h = date.getHours();
		int hm = (m > h) ? 0 : h - m;

		cld.set(date.getYear() + 1900, date.getMonth(), date.getDate(), hm,
				date.getMinutes(), date.getSeconds());

		return formatter.format(cld.getTime());
	}

	public static String getYearMonth() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);

		return str;
	}

	public static String convertToYearMonth(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);

		return str;
	}

	public static String convertMonthToYearMonth(Integer month) {
		Date date = new Date();
		date.setMonth(month.intValue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String str = formatter.format(date);

		return str;
	}

	public Date convertDateSplitMinuter(Date Date2, Integer m, Boolean flag) {
		Integer localInteger1 = m;
		Integer localInteger2 = m = Integer.valueOf(m.intValue() + 1);
		Long ntime = Long.valueOf(Date2.getTime() / 1000L);
		Long time = Long.valueOf((flag.booleanValue()) ? ntime.longValue() + 60
				* m.intValue() : ntime.longValue() - (60 * m.intValue()));
		Date2.setTime(time.longValue() * 1000L);

		return Date2;
	}

	public static String getYearMonthDay() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return formatter.format(date);
	}

	public static int dayIndex(Date date) {
		int h = date.getHours();
		int m = date.getMinutes();
		int count = h * 60 + m;
		int index = count / 30;

		return index;
	}

	public static int countIndex(Date date) {
		int week = date.getDay();
		int h = date.getHours();
		int m = date.getMinutes();
		int count = h * 60 + m;
		int index = week * 48 + count / 30;

		return index;
	}

	public static int monthIndex(Date date) {
		int day = date.getDate() - 1;
		int h = date.getHours();
		int count = day * 24 + h;

		return (count + 1);
	}

	public static List allMonth(Integer months) {
		List list = new ArrayList();
		Calendar cal = Calendar.getInstance();
		if (months.intValue() != cal.getTime().getMonth()) {
			cal.set(2, months.intValue());
			cal.set(5, cal.getLeastMaximum(5));
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int year = cal.getTime().getYear() + 1900;
		int month = cal.getTime().getMonth();
		int date = cal.getTime().getDate();
		int h = cal.getTime().getHours();
		cal.set(year, month, 1, 0, 0, 0);
		int i = 0;
		int c = 24;

		for (; i < date; ++i) {
			cal.set(5, i + 1);
			Date date1 = cal.getTime();
			if (i == date - 1)
				c = h;
			for (int j = 0; j < c; ++j) {
				long Time = date1.getTime() / 1000L + 3600L;
				date1.setTime(Time * 1000L);
				list.add(formatter.format(date1));
			}

		}

		return list;
	}

	public static List allWeek() {
		SimpleDateFormat formatter2 = new SimpleDateFormat("E HH:mm:ss");
		SimpleDateFormat formatter3 = new SimpleDateFormat("E");
		SimpleDateFormat formatter4 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		List list = new ArrayList();
		try {
			Date date1 = formatter4.parse("2008-08-01 00:00:00");
			for (int i = 0; i <= 336; ++i) {
				if ((date1.getHours() == 0) && (date1.getMinutes() == 0)
						&& (date1.getSeconds() == 0)) {
					list.add(String.valueOf(formatter3.format(date1)));
				} else
					list.add(String.valueOf(formatter2.format(date1)));
				long Time = date1.getTime() / 1000L + 1800L;
				date1.setTime(Time * 1000L);
			}
		} catch (Throwable t) {
			System.out.print(t);
		}

		return list;
	}

	public static List<String> allDay() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		List list = new ArrayList();
		try {
			Date date1 = formatter.parse("2008-08-01 00:00:00");
			for (int i = 0; i < 48; ++i) {
				list.add(String.valueOf(formatter.format(date1)));
				long Time = date1.getTime() / 1000L + 1800L;
				date1.setTime(Time * 1000L);
			}
		} catch (Throwable t) {
			log.error("", t);
		}

		return list;
	}

	public static String getLastDateSunday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(10, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(7, 1);
		cal.add(3, -1);

		return formatter.format(cal.getTime());
	}

	public static String getLastDateSaturday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(10, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.set(7, 7);
		cal.add(3, -1);

		return formatter.format(cal.getTime());
	}

	public static String getNextDateSunday() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(10, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(7, 1);
		cal.add(3, 0);

		return formatter.format(cal.getTime());
	}

	public static String getMonTableName(Date date) {
		int day = date.getDay();
		String yearMonth = new SimpleDateFormat("yyyyMM").format(date);
		if (day < 11)
			yearMonth = yearMonth + "1";
		else if (day < 21)
			yearMonth = yearMonth + "2";
		else {
			yearMonth = yearMonth + "3";
		}

		return yearMonth;
	}

	public static void getWeekTableName() {
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(10, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(7, 1);
	}

	public static String fmtWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(7);

		if (curDay != 7) {
			cal.add(5, 7 - curDay);
		}

		int week = cal.get(3);
		String weekStr = "0" + week;
		formatStr = cal.get(1) + weekStr;

		return formatStr;
	}

	public static String fmtNextWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(7);
		cal.add(5, 7 - curDay + 7);
		int week = cal.get(3);
		String weekStr = "0" + week;
		formatStr = cal.get(1) + weekStr;

		return formatStr;
	}

	public static Date parseStr2Date(String time, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(time);
		} catch (ParseException e) {
			log.warn("时间转换失败:" + time);
		}
		return null;
	}

	public static long time2Long(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(11);
		int minute = cal.get(12);
		int second = cal.get(13);
		return ((second + minute * 60 + hour * 60 * 60) * 1000);
	}

	public static String fmtDateToStr(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.format(date);
		} catch (Throwable t) {
			log.error("时间转换成格式字符串失败......", t);
		}

		return null;
	}

	public static boolean isInDate(Date begin, Date end) {
		Long now = Long.valueOf(new Date().getTime());

		return ((now.longValue() >= begin.getTime()) && (now.longValue() <= end
				.getTime()));
	}

	/**
	 * @Description: TODO 采集器调度中的信息之后可以看看
	 * @param workCal
	 * @return
	 */
	/*
	public static boolean isInWorkCal(WorkCalDTO workCal)
	{
	  try
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	    Date now = new Date();
	
	    if ((workCal.getWorkspecialconf() != null) && (!(workCal.getWorkspecialconf().trim().equals("")))) {
	      long nowTime = now.getTime();
	      String[] dateFields = workCal.getWorkspecialconf().split(",");
	
	      for (String dateField : dateFields) {
	        String[] dates = dateField.split("~");
	        long beginTime = sdf.parse(dates[1]).getTime();
	        long endTime = sdf.parse(dates[2]).getTime();
	
	        if ((nowTime >= beginTime) && (nowTime <= endTime))
	        {
	          return (!(dates[0].trim().equals("0")));
	        }
	
	      }
	
	    }
	
	    String nowDay = now.getDay() + "~";
	    if ((workCal.getWorkCalconf() != null) && (workCal.getWorkCalconf().contains(nowDay)))
	    {
	      now.setYear(70);
	      now.setMonth(0);
	      now.setDate(1);
	
	      long nowTime = now.getTime();
	      String[] days = workCal.getWorkCalconf().split(",");
	
	      for (String day : days) {
	        if (day.contains(nowDay)) {
	          String[] cfg = day.split("~");
	          long beginTime = sdfTime.parse(cfg[1]).getTime();
	          long endTime = sdfTime.parse(cfg[2]).getTime();
	
	          if ((nowTime >= beginTime) && (nowTime <= endTime)) {
	            return true;
	          }
	        }
	      }
	    }
	  }
	  catch (Throwable t)
	  {
	    log.error("工作时间对比失败......", t);
	  }
	
	  return false;
	}*/

	public static void main(String[] args) {
		Date date = parseStr2Date("13:37:45", "HH:mm:ss");
		System.out.println(time2Long(date));
	}
}
