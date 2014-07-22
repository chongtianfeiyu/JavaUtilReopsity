package com.yang.javalib.baseUtil.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TimeUtil class is a universal tools for date handling.
 * 
 * @author rainteen
 * @since 2008-09-08
 * @version 2.0
 */
@SuppressWarnings("all")
public class TimeUtil {
	private static Log log = LogFactory.getLog(TimeUtil.class);
	private static final int[] week = new int[] { Calendar.SUNDAY,
			Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
			Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY };

	/**
	 * "yyyy-MM-dd" Date Format
	 */
	public static final String YMD_horizon = "yyyy-MM-dd";
	/**
	 * "yyyy-MM-dd HH:mm:ss" Date Format
	 */
	public static final String YMD_horizon_time = "yyyy-MM-dd HH:mm:ss";
	/**
	 * "yyyyMMdd" Date Format
	 */
	public static final String YMD_long = "yyyyMMdd";
	/**
	 * "yyyyMMdd HH:mm:ss" Date Format
	 */
	public static final String YMD_long_time = "yyyyMMdd HH:mm:ss";
	/**
	 * "yyyy/MM/dd" Date Format
	 */
	public static final String YMD_slant1 = "yyyy/MM/dd";
	/**
	 * "yyyy/MM/dd HH:mm:ss" Date Format
	 */
	public static final String YMD_slant1_time = "yyyy/MM/dd HH:mm:ss";
	/**
	 * "MM/dd/yyyy" Date Format
	 */
	public static final String YMD_slant2 = "MM/dd/yyyy";
	/**
	 * "MM/dd/yyyy HH:mm:ss" Date Format
	 */
	public static final String YMD_slant2_time = "MM/dd/yyyy HH:mm:ss";
	/**
	 * "MM/dd,yyyy" USA Date Format
	 */
	public static final String YMD_usa = "MM/dd,yyyy";
	/**
	 * "MM/dd,yyyy HH:mm:ss" USA Date Format
	 */
	public static final String YMD_usa_time = "MM/dd,yyyy HH:mm:ss";
	/**
	 * "yyyyMMdd" Chinese Date Format
	 */
	public static final String YMD_chn = "yyyy年MM月dd日";
	/**
	 * "yyyyMMdd HH:mm:ss" Chinese Date Format
	 */
	public static final String YMD_chn_time = "yyyy年MM月dd日 HH:mm:ss";

	//用来全局控制 上一周，本周，下一周的周数变化   
	private static int weeks = 0;
	private static int MaxDate;//一月最大天数   
	private static int MaxYear;//一年最大天数   

	private TimeUtil() {
	}

	/**
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static String correctDate(int nYear, int nMonth, int nDay) {
		if (nYear < 0 || (nMonth < 0 || nMonth > 12)) {
			return null;
		}
		String date = null;
		Calendar calendar = null;
		if (nMonth == 0) {
			calendar = new GregorianCalendar(nYear, nMonth, nDay);
		} else {
			calendar = new GregorianCalendar(nYear, nMonth - 1, nDay);
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (month < 10) {
			date = year + "0" + month;
		} else {
			date = year + "" + month;
		}
		if (day < 10) {
			date = date + "0" + day;
		} else {
			date = date + "" + day;
		}
		return date;
	}

	/**
	 * 
	 * @param date
	 * @param isShort
	 * @return
	 */
	public static String correctDate(String date, boolean isShort) {
		if (!isShort && (date == null || date.length() != 8)) {
			return null;
		}
		if (isShort && (date == null || date.length() != 6)) {
			return null;
		}
		date = date.trim();
		String res = null;
		Calendar calendar = null;
		int nYear = 0, nMonth = 0, nDay = 0;

		if (!isShort) {
			nYear = Integer.parseInt(date.substring(0, 4));
			nMonth = Integer.parseInt(date.substring(4, 6));
			nDay = Integer.parseInt(date.substring(6, date.length()));
			if (nMonth == 0) {
				calendar = new GregorianCalendar(nYear, nMonth, nDay);
			} else {
				calendar = new GregorianCalendar(nYear, nMonth - 1, nDay);
			}
		} else {
			nYear = Integer.parseInt(date.substring(0, 2));
			nMonth = Integer.parseInt(date.substring(2, 4));
			nDay = Integer.parseInt(date.substring(4, date.length()));
			if (nMonth == 0) {
				calendar = new GregorianCalendar(nYear + 1900, nMonth, nDay);
			} else {
				calendar = new GregorianCalendar(nYear + 1900, nMonth - 1, nDay);
			}
		}
		res = Integer.valueOf(calendar.get(Calendar.YEAR)).toString();

		nMonth = calendar.get(Calendar.MONTH) + 1;
		if (nMonth < 10) {
			res = res
					+ "0"
					+ Integer.valueOf(calendar.get(Calendar.MONTH) + 1)
							.toString();
		} else {
			res = res
					+ Integer.valueOf(calendar.get(Calendar.MONTH) + 1)
							.toString();

		}
		nDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (nDay < 10) {
			res = res
					+ "0"
					+ Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
							.toString();
		} else {
			res = res
					+ Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
							.toString();
			// System.out.println(calendar);
		}
		return res;
	}

	/**
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static String correctDate(String strDate, String format) {
		String res = "";
		if (strDate == null || "".equals(strDate) || format == null
				|| "".equals(format)) {
			throw new IllegalArgumentException(".");
		}
		String reg = "^((yyyy年MM月dd日)|(yyyy\\-MM\\-dd)|(yyyy/MM/dd)|(MM/dd/yyyy)|(MM/dd,yyyy)|(yyyyMMdd)){1}(\\s{0,1}(HH:{0,1}){0,1}(mm:{0,1}){0,1}(ss){0,1}){0,1}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(format);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					".[(yyyy年MM月dd日) (yyyy-MM-dd) (yyyy/MM/dd) (MM/dd/yyyy) (MM/dd,yyyy) (yyyyMMdd) HH:mm:ss]");
		}
		strDate = strDate.trim();
		format = format.trim();
		Date date = null;
		DateFormat dateFormat = null;
		if (format.indexOf(":") != -1 && strDate.indexOf(":") == -1)
			strDate += " 00:00:00";
		try {
			if (format.indexOf(":") == -1)
				dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
			else
				dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			// System.out.println("Convert to: yyyy年MM月dd日");
			try {
				if (format.indexOf(":") == -1)
					dateFormat = new SimpleDateFormat(TimeUtil.YMD_chn);
				else
					dateFormat = new SimpleDateFormat(TimeUtil.YMD_chn_time);
				date = dateFormat.parse(strDate);
			} catch (ParseException e1) {
				// System.out.println("Convert to: yyyy-MM-dd");
				try {
					if (format.indexOf(":") == -1)
						dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
					else
						dateFormat = new SimpleDateFormat(
								TimeUtil.YMD_horizon_time);
					date = dateFormat.parse(strDate);
				} catch (ParseException e2) {
					try {
						if (strDate.indexOf("/") == 4
								&& strDate.lastIndexOf("/") == (strDate
										.length() - 3)) {
							// System.out.println("Convert to: yyyy/MM/dd");
							if (format.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant1);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant1_time);
						} else {
							// System.out.println("Convert to: MM/dd/yyyy");
							if (format.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant2);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant2_time);
						}
						date = dateFormat.parse(strDate);
					} catch (ParseException e3) {
						// System.out.println("Convert to: MM/dd,yyyy");
						try {
							if (format.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_usa);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_usa_time);
							date = dateFormat.parse(strDate);
						} catch (ParseException e5) {
							// System.out.println("Convert to: yyyyMMdd");
							try {
								if (format.indexOf(":") == -1)
									dateFormat = new SimpleDateFormat(
											TimeUtil.YMD_long);
								else
									dateFormat = new SimpleDateFormat(
											TimeUtil.YMD_long_time);
								date = dateFormat.parse(strDate);
							} catch (ParseException e6) {
								// e6.printStackTrace();
								log.error(e6);
								System.err.println("Not support format!");
							}
						}
					}
				}
			}
		}
		dateFormat = new SimpleDateFormat(format);
		if (date != null)
			res = dateFormat.format(date);
		return res;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
    @Deprecated
	public static Date dateFormat(String strDate) {
		String resDate = correctDate(strDate, TimeUtil.YMD_horizon_time);
		Date date = stringToDate(resDate);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
    @Deprecated
	public static String dateToString(Date date) {
		if (date == null)
			return null;
		String res = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		DateFormat dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param date
	 * @param onlyYMD
	 * @return
	 */
    @Deprecated
	public static String dateToString(Date date, boolean onlyYMD) {
		if (date == null)
			return null;
		String resDate = null;
		DateFormat dateFormat = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (onlyYMD) {
			dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
			resDate = dateFormat.format(calendar.getTime());
		} else {
			dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
			resDate = dateFormat.format(calendar.getTime());
		}
		return resDate;
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
    @Deprecated
	public static String dateToString(Date date, String format) {
		if (date == null)
			return null;
		if (format == null || "".equals(format.trim())) {
			format = TimeUtil.YMD_horizon_time;
		}
		String reg = "^((yyyy年MM月dd日)|(yyyy\\-MM\\-dd)|(yyyy/MM/dd)|(MM/dd/yyyy)|(MM/dd,yyyy)|(yyyyMMdd)){1}(\\s{0,1}(HH:{0,1}){0,1}(mm:{0,1}){0,1}(ss){0,1}){0,1}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(format);
		if (!matcher.matches()) {
			System.err
					.println(":[(yyyy年MM月dd日) (yyyy-MM-dd) (yyyy/MM/dd) (MM/dd/yyyy) (MM/dd,yyyy) (yyyyMMdd)]\n:"
							+ TimeUtil.YMD_horizon_time + " ");
			format = TimeUtil.YMD_horizon_time;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		DateFormat dformat = new SimpleDateFormat(format);
		String resDate = dformat.format(calendar.getTime());
		return resDate;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		int nYear = calendar.get(Calendar.YEAR);
		return nYear;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		int nMonth = calendar.get(Calendar.MONTH) + 1;
		return nMonth;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		int nDay = calendar.get(Calendar.DAY_OF_MONTH);
		return nDay;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar calendar = Calendar.getInstance();
		int nHour = calendar.get(Calendar.HOUR_OF_DAY);
		return nHour;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar calendar = Calendar.getInstance();
		int nMinute = calendar.get(Calendar.MINUTE);
		return nMinute;
	}

	/**
	 * 
	 * @param hasSplit
	 * @return
	 */
	public static String getCurrentTime(boolean hasSplit) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String res = "";
		if (hour < 10)
			res += "0" + hour;
		else
			res += hour;
		if (minute < 10) {
			if (hasSplit)
				res += ":0" + minute;
			else
				res += "0" + minute;
		} else {
			if (hasSplit)
				res += ":" + minute;
			else
				res += "" + minute;
		}
		if (second < 10) {
			if (hasSplit)
				res += ":0" + second;
			else
				res += "0" + second;
		} else {
			if (hasSplit)
				res += ":" + second;
			else
				res += "" + second;
		}
		return res;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(String date) {
		date = TimeUtil.correctDate(date, TimeUtil.YMD_long);
		int nYear = Integer.parseInt(date.substring(0, 4));
		return nYear;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(String date) {
		date = TimeUtil.correctDate(date, TimeUtil.YMD_long);
		int nMonth = Integer.parseInt(date.substring(4, 6));
		return nMonth;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		if (date == null) {
			return -1;
		}
		String strDate = TimeUtil.dateToString(date, TimeUtil.YMD_horizon);
		strDate = TimeUtil.correctDate(strDate, TimeUtil.YMD_horizon);
		return getDay(strDate);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(String date) {
		date = TimeUtil.correctDate(date, TimeUtil.YMD_long);
		int nDay = Integer.parseInt(date.substring(6, date.length()));
		return nDay;
	}

	/**
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		int nDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return nDayOfWeek;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(String date) {
		if (date == null) {
			return -1;
		}
		date = TimeUtil.correctDate(date, TimeUtil.YMD_long);
		int dayOfWeek = 0, nYear = 0, nMonth = 0, nDay = 0;

		nYear = Integer.parseInt(date.substring(0, 4));
		nMonth = Integer.parseInt(date.substring(4, 6));
		nDay = Integer.parseInt(date.substring(6, date.length()));

		Calendar calendar = new GregorianCalendar(nYear, nMonth - 1, nDay);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		return dayOfWeek;
	}

	/**
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static int getDayOfWeek(int nYear, int nMonth, int nDay) {
		int dayOfWeek = 0;
		String date = TimeUtil.correctDate(nYear, nMonth, nDay);
		if (date == null) {
			return -1;
		}
		nYear = Integer.parseInt(date.substring(0, 4));
		nMonth = Integer.parseInt(date.substring(4, 6));
		nDay = Integer.parseInt(date.substring(6, date.length()));

		Calendar calendar = new GregorianCalendar(nYear, nMonth - 1, nDay);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // -calendar.getFirstDayOfWeek();

		return dayOfWeek;
	}

	/**
	 * 
	 * @param strDate
	 * @param field
	 * @return
	 */
	public static Date getDayOfWeekByDateField(String strDate, int field) {
		Date date = TimeUtil.stringToDate(strDate);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int curWeek = calendar.get(Calendar.DAY_OF_WEEK);

		switch (field) {
		case Calendar.MONDAY:
			calendar.add(Calendar.DATE, Calendar.MONDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.MONDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.TUESDAY:
			calendar.add(Calendar.DATE, Calendar.TUESDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.TUESDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.WEDNESDAY:
			calendar.add(Calendar.DATE, Calendar.WEDNESDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.WEDNESDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.THURSDAY:
			calendar.add(Calendar.DATE, Calendar.THURSDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.THURSDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.FRIDAY:
			calendar.add(Calendar.DATE, Calendar.FRIDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.FRIDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.SATURDAY:
			calendar.add(Calendar.DATE, Calendar.SATURDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.SATURDAY-curWeek,
			// Calendar.DATE);
			break;
		case Calendar.SUNDAY:
			calendar.add(Calendar.DATE, Calendar.SUNDAY - curWeek);
			// TimeUtil.getDateAfter(date, Calendar.SUNDAY-curWeek,
			// Calendar.DATE);
			break;
		default:
			throw new RuntimeException("[" + field + "].");
		}
		return calendar.getTime();
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
    @Deprecated
	public static Date stringToDate(String strDate) {
		if (strDate == null || "".equals(strDate.trim()))
			return null;
		strDate = strDate.trim();
		Date date = null;
		DateFormat dateFormat = null;
		try {
			if (strDate.indexOf(":") == -1)
				dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
			else
				dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			// System.out.println("Convert to: yyyy年MM月dd日");
			try {
				if (strDate.indexOf(":") == -1)
					dateFormat = new SimpleDateFormat(TimeUtil.YMD_chn);
				else
					dateFormat = new SimpleDateFormat(TimeUtil.YMD_chn_time);
				date = dateFormat.parse(strDate);
			} catch (ParseException e1) {
				// System.out.println("Convert to: yyyy-MM-dd");
				try {
					if (strDate.indexOf(":") == -1)
						dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
					else
						dateFormat = new SimpleDateFormat(
								TimeUtil.YMD_horizon_time);
					date = dateFormat.parse(strDate);
				} catch (ParseException e2) {
					try {
						if (strDate.indexOf("/") == 4
								&& strDate.lastIndexOf("/") == (strDate
										.length() - 3)) {
							// System.out.println("Convert to: yyyy/MM/dd");
							if (strDate.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant1);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant1_time);
						} else {
							// System.out.println("Convert to: MM/dd/yyyy");
							if (strDate.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant2);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_slant2_time);
						}
						date = dateFormat.parse(strDate);
					} catch (ParseException e3) {
						// System.out.println("Convert to: MM/dd,yyyy");
						try {
							if (strDate.indexOf(":") == -1)
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_usa);
							else
								dateFormat = new SimpleDateFormat(
										TimeUtil.YMD_usa_time);
							date = dateFormat.parse(strDate);
						} catch (ParseException e5) {
							// System.out.println("Convert to: yyyyMMdd");
							try {
								if (strDate.indexOf(":") == -1)
									dateFormat = new SimpleDateFormat(
											TimeUtil.YMD_long);
								else
									dateFormat = new SimpleDateFormat(
											TimeUtil.YMD_long_time);
								date = dateFormat.parse(strDate);
							} catch (ParseException e6) {
								log.error(e6);
							}
						}
					}
				}
			}
		}
		return date;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String int2date(int data) {
		String st_return = "";
		try {
			DateFormat daf_date = DateFormat.getDateInstance(DateFormat.MEDIUM,
					Locale.CHINA);
			daf_date.parse("1899-12-31");
			Calendar cal = daf_date.getCalendar();
			cal.add(Calendar.DATE, data);
			String st_m = "";
			String st_d = "";
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DAY_OF_MONTH);
			if (m <= 9) {
				st_m = "0" + m;
			} else {
				st_m = "" + m;
			}
			if (d <= 9) {
				st_d = "0" + d;
			} else {
				st_d = "" + d;
			}
			st_return = y + st_m + st_d;

		} catch (ParseException e) {
			log.error(e);
			st_return = TimeUtil.timeMillis2Format("yyyyMMdd");
		}
		return st_return;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @param isShort
	 * @return
	 */
	public static int getDaysBetween(String start, String end, boolean isShort) {
		String startDate = TimeUtil.correctDate(start, isShort);
		String endDate = TimeUtil.correctDate(end, isShort);

		Calendar calStart = new GregorianCalendar(getYear(startDate),
				getMonth(startDate) - 1, getDay(startDate));
		Calendar calEnd = new GregorianCalendar(getYear(endDate),
				getMonth(endDate) - 1, getDay(endDate));
		long lngStart = calStart.getTimeInMillis();
		long lngEnd = calEnd.getTimeInMillis();
		if (lngStart > lngEnd) {
			return -1;
		}
		int nDay = (int) ((lngEnd - lngStart) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 
	 * @param nYear
	 * @return
	 */
	public static int getTotalDaysOfYear(int nYear) {
		Calendar calendar = new GregorianCalendar(nYear, 11, 31);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 
	 * @param nYear
	 * @param nMonth
	 * @return
	 */
	public static int getTotalDaysOfMonth(int nYear, int nMonth) {
		int nDays = 0;
		if (nMonth <= 0 || nMonth > 12) {
			return 0;
		}
		Calendar calendar = new GregorianCalendar(nYear, nMonth, 31);
		if (nMonth > 1) {
			//
			calendar.set(Calendar.MONTH, nMonth);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.roll(Calendar.MONTH, false);
		} else {
			calendar.set(Calendar.MONTH, nMonth - 1);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.roll(Calendar.MONTH, false);
		}
		//
		nDays = calendar.get(Calendar.DAY_OF_MONTH);
		return nDays;
	}

	/**
	 * 
	 * @return
	 */
    @Deprecated
	public static String timeMillis2Format() {
		String res = "0000-00-00 00:00:00";
		long timeMillis = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param timeMillis
	 * @return
	 */
    @Deprecated
	public static String timeMillis2Format(long timeMillis) {
		String res = "0000-00-00 00:00:00";
		if (timeMillis <= 0) {
			timeMillis = System.currentTimeMillis();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon_time);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param formatStr
	 * @return
	 */
    @Deprecated
	public static String timeMillis2Format(String formatStr) {
		if (formatStr == null || "".equals(formatStr)) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		String reg = "^((yyyy年MM月dd日)|(yyyy\\-MM\\-dd)|(yyyy/MM/dd)|(MM/dd/yyyy)|(MM/dd,yyyy)|(yyyyMMdd)){1}(\\s{0,1}(HH:{0,1}){0,1}(mm:{0,1}){0,1}(ss){0,1}){0,1}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(formatStr);
		String res = "0000-00-00 00:00:00";
		long timeMillis = System.currentTimeMillis();
		if (!matcher.matches()) {
			formatStr = TimeUtil.YMD_horizon_time;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(formatStr);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param timeMillis
	 * @param formatStr
	 * @return
	 */
    @Deprecated
	public static String timeMillis2Format(long timeMillis, String formatStr) {
		if (formatStr == null || "".equals(formatStr)) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		if (formatStr.indexOf("yy") < 0 || formatStr.indexOf("MM") < 0) {
			throw new IllegalArgumentException(
					"[(yyyy年MM月dd日) (yyyy-MM-dd) (yyyy/MM/dd) (MM/dd/yyyy) (MM/dd,yyyy) (yyyyMMdd) HH:mm:ss]");
		}
		String reg = "^((yyyy年MM月dd日)|(yyyy\\-MM\\-dd)|(yyyy/MM/dd)|(MM/dd/yyyy)|(MM/dd,yyyy)|(yyyyMMdd)){1}(\\s{0,1}(HH:{0,1}){0,1}(mm:{0,1}){0,1}(ss){0,1}){0,1}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(formatStr);
		String res = "0000-00-00 00:00:00";
		if (timeMillis <= 0) {
			timeMillis = System.currentTimeMillis();
		}
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					":[(yyyy年MM月dd日) (yyyy-MM-dd) (yyyy/MM/dd) (MM/dd/yyyy) (MM/dd,yyyy) (yyyyMMdd) HH:mm:ss].");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(formatStr);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @return
	 */
    @Deprecated
	public static String timeMillis2LongDate() {
		String res = "0000-00-00";
		long timeMillis = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param timeMillis
	 * @return
	 */
    @Deprecated
	public static String timeMillis2LongDate(long timeMillis) {
		String res = "0000-00-00";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		DateFormat dateFormat = new SimpleDateFormat(TimeUtil.YMD_horizon);
		res = dateFormat.format(calendar.getTime());
		return res;
	}

	/**
	 * 
	 * @param nYear
	 * @param nMonth
	 * @return
	 */
	public static String getEndOfMonth(int nYear, int nMonth) {
		String date = "";
		if (nMonth < 1 || nMonth > 12) {
			return null;
		}
		int[] mMonth = new int[] { 1, 3, 5, 7, 8, 10, 12 };
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, nYear);
		calendar.set(Calendar.MONTH, nMonth - 1);
		//
		boolean bflag = false;
		for (int i = 0; i < mMonth.length; i++) {
			if (nMonth == mMonth[i]) {
				calendar.set(Calendar.DATE, 31);
				bflag = true;
				break;
			}
		}
		if (!bflag) {
			calendar.set(Calendar.DATE, 30);
		}
		if (nMonth == 2 && ((GregorianCalendar) calendar).isLeapYear(nYear)) {
			calendar.set(Calendar.DATE, 29);
		} else if (nMonth == 2) {
			calendar.set(Calendar.DATE, 28);
		} else
			;

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		if (month < 10) {
			date = year + "-0" + month;
		} else {
			date = year + "-" + month;
		}
		if (day < 10) {
			date = date + "-0" + day;
		} else {
			date = date + "-" + day;
		}
		return date;
	}

	/**
	 * 
	 * @param nYear
	 * @param nMonth
	 * @return
	 */
	public static String getEndOfMon(int nYear, int nMonth) {
		String date = "";
		if (nMonth < 1 || nMonth > 12) {
			return null;
		}
		// int[] mMonth=new int[]{1,3,5,7,8,10,12};
		// int[] lMonth=new int[]{2,4,6,9,11};
		String day = "";
		//
		boolean bflag = false;
		if (1 == nMonth || 3 == nMonth || 5 == nMonth || 7 == nMonth
				|| 8 == nMonth || 10 == nMonth || 12 == nMonth) {
			day = "" + 31;
			bflag = true;
		}
		if (!bflag) {
			day = "" + 30;
		}
		if (nMonth == 2) {
			Calendar calendar = new GregorianCalendar();
			calendar.set(Calendar.YEAR, nYear);
			if (((GregorianCalendar) calendar).isLeapYear(nYear))
				day = "" + 29;
			else
				day = "" + 28;
		}
		if (0 < nMonth && nMonth < 10) {
			date = nYear + "-0" + nMonth + "-" + day;
		} else {
			date = nYear + "-" + nMonth + "-" + day;
		}
		return date;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static int getQuarter(String strDate) {
		int month = TimeUtil.getMonth(strDate);
		switch (month) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		default:
			;
		}
		return -1;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getQuarter(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		switch (month) {
		case 1:
		case 2:
		case 3:
			return 1;
		case 4:
		case 5:
		case 6:
			return 2;
		case 7:
		case 8:
		case 9:
			return 3;
		case 10:
		case 11:
		case 12:
			return 4;
		default:
			;
		}
		return -1;
	}

	/**
	 * 
	 * @param strDate
	 * @return
	 */
	public static int getPeriodOfTenDays(String strDate) {
		Date date = TimeUtil.stringToDate(strDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		if (1 <= day && day <= 10) {
			return -1;
		} else if (11 <= day && day <= 20) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getPeriodOfTenDays(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		if (1 <= day && day <= 10) {
			return -1;
		} else if (11 <= day && day <= 20) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 
	 * @param strDate
	 * @param num
	 * @param field
	 * @return
	 */
	public static Date getDateAfter(String strDate, int num, int field) {
		Date date = TimeUtil.stringToDate(strDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		if (field == Calendar.YEAR) {
			calendar.add(Calendar.YEAR, num);
		} else if (field == Calendar.MONTH) {
			calendar.add(Calendar.MONTH, num);
		} else if (field == Calendar.DATE) {
			calendar.add(Calendar.DATE, num);
		} else
			;
		return calendar.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param num
	 * @param field
	 * @return
	 */
	public static Date getDateAfter(Date date, int num, int field) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		if (field == Calendar.YEAR) {
			calendar.add(Calendar.YEAR, num);
		} else if (field == Calendar.MONTH) {
			calendar.add(Calendar.MONTH, num);
		} else if (field == Calendar.DATE) {
			calendar.add(Calendar.DATE, num);
		} else
			;
		return calendar.getTime();
	}

	/**
	 * 
	 * @param strDate
	 * @param strDateAnother
	 * @return
	 */
	public static boolean isLatter(String strDate, String strDateAnother) {
		Date date = TimeUtil.stringToDate(strDate);
		Date anotherDate = TimeUtil.stringToDate(strDateAnother);
		if (date != null && anotherDate != null
				&& date.compareTo(anotherDate) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param strDate
	 * @param strDateAnother
	 * @return
	 */
	public static boolean isSameQuarter(String strDate, String strDateAnother) {
		int quarter = TimeUtil.getQuarter(strDate);
		int quarterAnother = TimeUtil.getQuarter(strDateAnother);
		if (quarter > 0 && quarter == quarterAnother) {
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * @param strDate
	 * @param strDateAnother
	 * @return
	 */
	public static boolean isSameWeek(String strDate, String strDateAnother) {
		Date date = TimeUtil.getDayOfWeekByDateField(strDate, Calendar.MONDAY);
		Date dateAnother = TimeUtil.getDayOfWeekByDateField(strDateAnother,
				Calendar.MONDAY);
		if (date.compareTo(dateAnother) == 0) {
			return true;
		} else
			return false;
	}

	/**
	 * 得到二个日期间的间隔天数 
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 得到二个日期间的间隔小时 
	 * @param sj1 "yyyy-MM-dd HH:mm"
	 * @param sj2 "yyyy-MM-dd HH:mm"
	 * @return
	 */
	public static long getTwoDayHours(String start, String end, String format) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH");
		if (format != null && !"".equals(format)) {
			myFormatter = new SimpleDateFormat(format);
		}
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(start);
			java.util.Date mydate = myFormatter.parse(end);
			long between = date.getTime() - mydate.getTime();
			if (between < 0) {
				between = between * -1;
			}
			day = (between) / (60 * 60 * 1000);
		} catch (Exception e) {
			return -1;
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔分钟 
	 * @param sj1 "yyyy-MM-dd HH:mm"
	 * @param sj2 "yyyy-MM-dd HH:mm"
	 * @return
	 */
	public static String getTwoDayMinutes(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			long between = date.getTime() - mydate.getTime();
			if (between < 0) {
				between = between * -1;
			}
			day = (between) / (60 * 1000);
		} catch (Exception e) {
			return "-1";
		}
		return day + "";
	}

	/**  
	 * 根据一个日期，返回是星期几的字符串    
	 * @param sdate  
	 * @return  
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间   
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);   
		// hour中存的就是星期几了，其范围 1~7   
		// 1=星期日 7=星期六，其他类推   
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static int getWeekInt(String sdate) {
		// 再转换为时间   
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);   
		// hour中存的就是星期几了，其范围 1~7   
		// 1=星期日 7=星期六，其他类推   
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 是否为该月第一天
	 */
	public static boolean isFirstDayOfMonth(String sdate) {

		Date date = TimeUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH) == 1 ? true : false;
	}

	/**  
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd   
	 *   
	 * @param strDate  
	 * @return  
	 */
    @Deprecated
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

    @Deprecated
	public static Date strToDate(String strDate, String formatStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

    @Deprecated
	public static String formatDateToStr(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//可以方便地修改日期格式      
		String nowStr = dateFormat.format(date);
		return nowStr;
	}

	/**  
	 * 两个时间之间的天数  
	 *   
	 * @param date1  
	 * @param date2  
	 * @return  
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间   
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 计算当月最后一天,返回字符串
	 * @return
	 */
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号   
		lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号   
		lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天   

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 上月第一天
	 * @return
	 */
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号   
		lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1号   
		//lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天   

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取当月第一天
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得本周星期日的日期
	 * @return
	 */
	public static String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当天时间
	 * @param dateformat
	 * @return
	 */
	public static String getNowTimeStr(String dateformat) {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式      
		String nowStr = dateFormat.format(now);
		return nowStr;
	}

	/**
	 * @throws ParseException 
	 * @Title: getNowTime
	 * @Description: TODO
	 * @param @param dateformat
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date getNowTime(String dateformat) throws ParseException {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式      

		String nowStr = dateFormat.format(now);

		return dateFormat.parse(nowStr);
	}

	/**
	 * 当前时间
	 * @param dateformat
	 * @return
	 */
	public static Date getNowDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * @return
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......   
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //因为按中国礼拜一作为第一天所以这里减1   
		System.out.println("相差天数:" + dayOfWeek);
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得本周一的日期
	 * @return
	 */
	public static String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得相应周的周六的日期
	 * @return
	 */
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得上周星期日的日期
	 * @return
	 */
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得上周星期一的日期
	 * @return
	 */
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得下周星期一的日期
	 * @return
	 */
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得下周星期日的日期
	 * @return
	 */
	public static String getNextSunday() {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);//把日期设置为当月第一天    
		cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天    
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	/**
	 * 获得上月最后一天的日期
	 * @return
	 */
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);//减一个月   
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天    
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天    
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月第一天的日期
	 * @return
	 */
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//减一个月   
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天    
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月最后一天的日期
	 * @return
	 */
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//加一个月   
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天    
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天    
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得明年最后一天的日期
	 * @return
	 */
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//加一个年   
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得明年第一天的日期
	 * @return
	 */
	public static String getNextYearFirst() {
		weeks--;
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天   
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。   
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR); //获得本年有多少天   
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, MaxYear - (yearPlus * weeks)
				+ 2);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;

	}

	/**
	 * 获得本年有多少天
	 * @return
	 */
	private static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天   
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。   
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	/**
	 * 
	 * @return
	 */
	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天   
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天   
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。   
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 获得本年第一天的日期
	 * @return
	 */
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	/**
	 * 获得本年最后一天的日期
	 * @return
	 */
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式      
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	/**
	 * 获得上年第一天的日期
	 * @return
	 */
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式      
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	/**
	 * 获得上年最后一天的日期
	 * @return
	 */
	public static String getPreviousYearEnd() {
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	/**
	 * 获得本季度
	 * @param month
	 * @return
	 */
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式      
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);   
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**  
	 * 获取某年某月的最后一天  
	 * @param year 年  
	 * @param month 月  
	 * @return 最后一天  
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**  
	 * 是否闰年  
	 * @param year 年  
	 * @return   
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 当前时间戳
	 * @return
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 转换时间戳
	 * @return
	 */
	public static String getTimestampToString(Timestamp datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(datetime);
	}

	/**
	 * 转换日期为字符串(12小时制)
	 * @param date
	 * @return
	 */
	public static String dateFormatToString(Date date) {
		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateFormat = null;

		try {
			dateFormat = format.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		return dateFormat;
	}

	/**
	 * 转换字符为日期型
	 * @param date
	 * @return
	 */
	public static Date StringFormatToDate(String date) {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFormat = null;

		try {
			dateFormat = (Date) format.parseObject(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(date
					+ " is not in valid Date format.");
		}

		return dateFormat;
	}

	/**
	 * 字符转化成时间戳
	 * @param str
	 * @return
	 */
	public static Timestamp StringToTimestamp(String str) {
		if (str == null) {
			return null;
		}

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return new Timestamp(df.parse(str).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(str
					+ " is not in valid Timestamp format.");
		}
	}

	public static int getMonthDays(String month) {

		Calendar cal = Calendar.getInstance();
		//或者用Calendar   cal   =   new   GregorianCalendar();
		//String date = "2010-07";	    
		try {
			/**设置date**/
			SimpleDateFormat oSdf = new SimpleDateFormat("", Locale.ENGLISH);
			oSdf.applyPattern("yyyy-MM");
			//System.out.println(oSdf.parse(month));  
			cal.setTime(oSdf.parse(month));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/**开始用的这个方法获取月的最大天数，总是得到是31天**/
		//int num = cal.getMaximum(Calendar.DAY_OF_MONTH);  
		/**开始用的这个方法获取实际月的最大天数**/
		int num2 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println(num2);  
		return num2;

	}

	public static void main(String[] args) {

		System.out.println(getTwoDayHours("2010-09-02", "2010-09-03",
				"yyyy-MM-dd"));

	}
}
