package com.yang.javalib.baseUtil.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.validator.DateValidator;
import com.yang.base.check.CheckUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Date Util
 * @author Jinson Chen
 *
 */

public class DateUtil_1 {

	private final static Logger logger = LogManager.getLogger(DateUtil_1.class);

	public final static String YEAR = "yyyy";

	public final static String MONTH = "MM";

	public final static String TRIM_MONTH = "M";

	public final static String DAY = "dd";

	public final static String DATE = YEAR + "-" + MONTH + "-" + DAY;

	public final static String HOUR = "HH";

	public final static String MINUTE = "mm";

	public final static String SECOND = "ss";

	public final static String TIME = HOUR + ":" + MINUTE;

	public final static String TIME1 = HOUR + ":" + MINUTE + ":" + SECOND;

	public final static String DATETIME = DATE + " " + TIME;

	public final static String DATEHOUR = DATE + " " + HOUR;

	public final static String DATESECOND = DATE + " " + TIME1;

	public final static String FIELDDATE = YEAR + MONTH + DAY;

	public final static String FIELDDATE2 = MONTH + DAY;

	public final static String FIELDTIME = HOUR + MINUTE;

	public final static String FIELDTIME1 = HOUR + MINUTE + SECOND;

	public final static String FIELD = FIELDDATE + " " + FIELDTIME;

	public static Date currentDateTime() {
		return new Date(System.currentTimeMillis());
	}

	public static Date getYesterday() {
		return changeDate(currentDateTime(), Calendar.DATE, -1);
	}

	public static Date changeDate(Date date, int field, int amount) {
		if (CheckUtil.isEmpty(date))
			return null;
		if (amount == 0)
			return date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static boolean isValidPeriod(Date fromDate, Date toDate) {
		return !CheckUtil.isEmpty(fromDate) && !CheckUtil.isEmpty(toDate)
				&& !fromDate.after(toDate);
	}

	public static String format(Date date, String pattern) {
		if (CheckUtil.isEmpty(date) || CheckUtil.isEmpty(pattern))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String format(Date date) {
		//default "yyyy-MM-dd HH:mm:ss"
		return formatDateSecond(date);
	}

	public static String formatDay(Date date) {
		return format(date, DAY);
	}

	public static String formatDate(Date date) {
		return format(date, DATE);
	}

	public static String formatTime(Date date) {
		return format(date, TIME);
	}

	public static String formatDateTime(Date date) {
		return format(date, DATETIME);
	}

	public static String formatDateSecond(Date date) {
		return format(date, DATESECOND);
	}

	public static String formatFieldDate(Date date) {
		return format(date, FIELDDATE);
	}

	public static String formatFieldTime(Date date) {
		return format(date, FIELDTIME);
	}

	public static Date parse(String value) {
		if (CheckUtil.isEmpty(value))
			return null;
		try {
			CheckUtil.checkEmpty("parse.String", DATESECOND);
			SimpleDateFormat sdf = new SimpleDateFormat(DATESECOND);
			sdf.setLenient(false); // Add by Vito
			return sdf.parse(value);
		} catch (ParseException exc) {
			try {
				SimpleDateFormat df2 = new SimpleDateFormat(DATE);
				return df2.parse(value);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date parse(String value, String pattern) {
		if (CheckUtil.isEmpty(value))
			return null;
		CheckUtil.checkEmpty("parse.String", pattern);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false); // Add by Vito
			return sdf.parse(value);
		} catch (ParseException exc) {
			// logger.error(exc.getMessage(), exc);
		}
		return null;
	}

	public static Date parseField(String date, String time) {
		if (CheckUtil.isEmpty(date) || CheckUtil.isEmpty(time))
			return null;
		return parse(date + " " + time, FIELD);
	}

	public static Date parseFieldDate(String date) {
		return parse(date, FIELDDATE);
	}

	public static Date parseFieldEndDate(String date) {
		Date endDate = parse(date, FIELDDATE);

		if (!CheckUtil.isEmpty(endDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			endDate = cal.getTime();
		}

		return endDate;
	}

	public static java.util.Date parseDate(java.sql.Date paramDate) {
		return paramDate;
	}

	public static java.sql.Date parseSqlDate(java.util.Date paramDate) {
		if (paramDate != null)
			return new java.sql.Date(paramDate.getTime());
		return null;
	}

	public static boolean isValidDate(String date, String format) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(date, format, true);
	}

	public static boolean isValidField(String date, String time) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(date + " " + time, FIELD, true);
	}

	public static boolean isValidFieldDate(String date) {
		DateValidator validator = DateValidator.getInstance();
		return validator.isValid(date, FIELDDATE, true);
	}

	public static int getCurrentYear() {
		return Integer.parseInt(DateUtil_1.format(DateUtil_1.currentDateTime(),
				DateUtil_1.YEAR));
	}

	public static int getCurrentMonth() {
		return Integer.parseInt(DateUtil_1.format(DateUtil_1.currentDateTime(),
				DateUtil_1.MONTH));
	}

	public static int toInt(Date date) {
		if (CheckUtil.isEmpty(date))
			return CheckUtil.NULL_INT;
		return Integer.parseInt(format(date, YEAR + MONTH + DAY));
	}

	public static Integer toInteger(Date date) {
		if (CheckUtil.isEmpty(date))
			return null;
		return new Integer(format(date, YEAR + MONTH + DAY));
	}

	public static int getDayOfWeek(Date date) {
		if (CheckUtil.isEmpty(date))
			return CheckUtil.NULL_INT;
		// Sunday = 1
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static Date getSystemEndDate() {
		return parseFieldEndDate("29991231");
	}

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getCurrentDateTime() {
		return currentDateTime();
	}

	public static String getCurrentDateString() {
		return format(currentDateTime());
	}

	public static String getCurrentDateString(String format) {
		return format(currentDateTime(), format);
	}

	public static int compareDate(String date1, String date2) {
		Date d1 = DateUtil_1.parse(date1, DateUtil_1.DATE);
		Date d2 = DateUtil_1.parse(date2, DateUtil_1.DATE);
		return compareDateTime(DateUtil_1.formatDateTime(d1),
				DateUtil_1.formatDateTime(d2));
	}

	public static int compareDateTime(String datetime1, String datetime2) {
		Date d1 = DateUtil_1.parse(datetime1, DateUtil_1.DATETIME);
		Date d2 = DateUtil_1.parse(datetime2, DateUtil_1.DATETIME);
		return d1.compareTo(d2);
	}

	public static int compareDateSecond(String datetime1, String datetime2) {
		Date d1 = DateUtil_1.parse(datetime1, DateUtil_1.DATESECOND);
		Date d2 = DateUtil_1.parse(datetime2, DateUtil_1.DATESECOND);
		return d1.compareTo(d2);
	}

	/**
	 * 
	 * Title: daysOpera
	 * Description:
	 * 		 add n Days or dec n Days
	 * @param dateStr
	 * @param pattern
	 * @param days
	 * @return
	 */
	public static String daysOpera(String dateStr, String pattern, int days) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil_1.parse(dateStr, pattern));
		date = DateUtil_1.changeDate(date, Calendar.DATE, days);
		dateStr = DateUtil_1.format(date, pattern);
		return dateStr;
	}

	/**
	 * 
	 * Title: calendarOpera
	 * Description: 
	 *				
	 * @param dateStr
	 * @param pattern
	 * @param yearMonthDayOpera  operation about year\month\day  
	 * @return  String
	 */
	public static String calendarOpera(String dateStr, String pattern,
			int yearMonthDayOpera[]) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil_1.parse(dateStr, pattern));
		int yearOpera = yearMonthDayOpera[0];
		int monthOpera = yearMonthDayOpera[1];
		int dayOpera = yearMonthDayOpera[2];
		System.out.println(yearOpera + "  " + monthOpera + "  " + dayOpera);
		cal.add(Calendar.YEAR, yearOpera);
		cal.add(Calendar.MONTH, monthOpera);
		cal.add(Calendar.DAY_OF_MONTH, dayOpera);
		date = cal.getTime();
		dateStr = DateUtil_1.format(date, pattern);
		return dateStr;
	}

	/**
	 * 
	 * Title: addOneDay
	 * Description: add 1 Day
	 * @param dateStr
	 * @param pattern
	 * @return String
	 */
	public static String addOneDay(String dateStr, String pattern) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil_1.parse(dateStr, pattern));
		date = DateUtil_1.changeDate(date, Calendar.DATE, +1);
		dateStr = DateUtil_1.format(date, pattern);
		return dateStr;
	}

	/**
	 * 
	 * Title: decOneDay
	 * Description:  dec 1 Day		
	 * @param dateStr
	 * @param pattern
	 * @return  String
	 */
	public static String decOneDay(String dateStr, String pattern) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil_1.parse(dateStr, pattern));
		date = DateUtil_1.changeDate(date, Calendar.DATE, -1);
		dateStr = DateUtil_1.format(date, pattern);
		return dateStr;
	}

	/**
	 * 
	 * Title: decOneDay
	 * Description: dec One day
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String decOneDay(Date date, String pattern) {
		if (CheckUtil.isEmpty(date))
			date = DateUtil_1.currentDateTime();
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		date = DateUtil_1.changeDate(date, Calendar.DATE, -1);
		dateStr = DateUtil_1.format(date, pattern);
		return dateStr;
	}

	/**
	 * 
	 * Title: changeDateStrFormat
	 * Description: change String date Format patternSrc to patternDst 
	 * @param dateStr
	 * @param patternSrc
	 * @param patternDst
	 * @return  String
	 */
	public static String changeDateStrFormat(String dateStr, String patternSrc,
			String patternDst) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, patternSrc);
		dateStr = DateUtil_1.format(date, patternDst);
		return dateStr;
	}

	/**
	 * 
	 * Title: changeDateFormat
	 * Description: change  dateStr Format patternSrc to patternDst 
	 * @param dateStr
	 * @param patternSrc
	 * @param patternDst
	 * @return  Date
	 */
	public static Date changeDateFormat(String dateStr, String patternSrc,
			String patternDst) {
		if (CheckUtil.isEmpty(dateStr))
			return null;
		Date date = DateUtil_1.parse(dateStr, patternSrc);
		dateStr = DateUtil_1.format(date, patternDst);
		date = DateUtil_1.parse(dateStr, patternDst);
		return date;
	}

	/**
	 * 
	 * Title: changeDateFormat
	 * Description: change  date Format patternSrc to patternDst 
	 * @param date
	 * @param patternSrc
	 * @param patternDst
	 * @return Date
	 */
	public static Date changeDateFormat(Date date, String patternSrc,
			String patternDst) {
		if (CheckUtil.isEmpty(date))
			return null;
		String dateStr = DateUtil_1.format(date, patternSrc);
		date = DateUtil_1.parse(dateStr, patternDst);
		return date;
	}

	/**
	 * 天数操作
	 * @author Jinson Chen
	 */

	public static String dayAdd(String date, int days) {
		SimpleDateFormat sf = new SimpleDateFormat(DATESECOND);
		try {
			Date formatDate = new Date(sf.parse(date).getTime());
			Date changDate = changeDate(formatDate, Calendar.DATE, days);
			return formatDateSecond(changDate);

		} catch (ParseException e) {
			throw new IllegalArgumentException(date
					+ " is not in valid Date format.");
		}
	}

	public static Date dayAdd(Date date, int days) {
		return changeDate(date, Calendar.DATE, days);
	}

	//基于当日加减
	public static String dayAdd(int days) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day + days);
		Date cc = calendar.getTime();
		return form.format(cc);
	}

	//月数操作
	public static String monthAdd(int months) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + months);
		Date cc = calendar.getTime();
		return form.format(cc);
	}

	public static String monthAdd(String monthDate, int months) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		try {
			Date formatDate = new Date(format.parse(monthDate).getTime());
			calendar.setTime(formatDate);
			calendar.add(Calendar.MONTH, months);
			Date cc = calendar.getTime();
			return format.format(cc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String yearAdd(String yearDate, int years) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			Date formatDate = new Date(format.parse(yearDate).getTime());
			calendar.setTime(formatDate);
			calendar.add(Calendar.YEAR, years);
			Date cc = calendar.getTime();
			return format.format(cc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/** 列出时间段内所有日期s **/
	public static String[] getDatesFromTo(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return null;
		int days = getDays(dateFrom, dateTo) + 1;
		if (days == 0)
			return null;
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < days; i++) {
			str.append(getDateAddDays(dateFrom, i) + "|");
		}
		if (str.length() == 0)
			return null;
		String[] dates = str.toString()
				.substring(0, str.toString().length() - 1).split("\\|");
		return dates;
	}

	public static String[] getMonthsFromTo(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return null;
		int months = getMonths(dateFrom, dateTo) + 1;
		if (months == 0)
			return null;
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < months; i++) {
			str.append(monthAdd(dateFrom, i) + "|");
		}
		if (str.length() == 0)
			return null;
		String[] dates = str.toString()
				.substring(0, str.toString().length() - 1).split("\\|");
		return dates;
	}

	public static String[] getYearsFromTo(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return null;
		StringBuffer str = new StringBuffer();
		String formatDateFrom = DateUtil_1.format(
				DateUtil_1.parse(dateFrom, "yyyy"), "yyyy");
		String formatDateTo = DateUtil_1.format(
				DateUtil_1.parse(dateTo, "yyyy"), "yyyy");
		if (formatDateFrom != null && formatDateTo != null) {
			int distYear = Integer.parseInt(formatDateTo)
					- Integer.parseInt(formatDateFrom) + 1;
			for (int i = 0; i < distYear; i++) {
				str.append(yearAdd(dateFrom, i) + "|");
			}
		}
		if (str.length() == 0)
			return null;
		String[] dates = str.toString()
				.substring(0, str.toString().length() - 1).split("\\|");
		return dates;
	}

	public static String[] getWeeksFromTo(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return null;

		Date startDate = parse(dateFrom, "yyyy-MM-dd");
		Date endDate = parse(dateTo, "yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);
		int beginWeekIndex = getWeekOfYear(startDate);
		//System.out.println("beginCalendar:"+beginWeekIndex);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		int endWeekIndex = getWeekOfYear(endDate);
		//System.out.println("endCalendar:"+endWeekIndex);

		StringBuffer weeksBf = new StringBuffer();
		if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
				&& beginCalendar.get(Calendar.MONTH) == endCalendar
						.get(Calendar.MONTH) && beginWeekIndex == endWeekIndex) {
			String weekOfYearStr = "";
			if (beginWeekIndex < 10) {
				weekOfYearStr = "0" + beginWeekIndex;
			} else {
				weekOfYearStr = beginWeekIndex + "";
			}
			weeksBf.append(weekOfYearStr + "|");
			weeksBf.append(beginWeekIndex + "|");
			return weeksBf.toString()
					.substring(0, weeksBf.toString().length() - 1).split("\\|");
		}

		while (beginCalendar.before(endCalendar)) {
			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环  
			if (beginCalendar.get(Calendar.YEAR) == endCalendar
					.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar
							.get(Calendar.MONTH)
					&& beginWeekIndex == endWeekIndex) {
				break;
			} else {
				int weekOfYear = getWeekOfYear(beginCalendar.getTime());
				String weekOfYearStr = "";
				if (weekOfYear < 10) {
					weekOfYearStr = "0" + weekOfYear;
				} else {
					weekOfYearStr = weekOfYear + "";
				}
				weeksBf.append(weekOfYearStr + "|");
				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		//System.out.println(getWeekOfYear(beginCalendar.getTime()));
		if ((getWeekOfYear(beginCalendar.getTime()) - 1) != endWeekIndex) {
			String weekOfYearStr = "";
			if (endWeekIndex < 10) {
				weekOfYearStr = "0" + endWeekIndex;
			} else {
				weekOfYearStr = endWeekIndex + "";
			}
			weeksBf.append(weekOfYearStr + "|");
		}

		return weeksBf.toString().substring(0, weeksBf.toString().length() - 1)
				.split("\\|");

	}

	/**
	 * 计算两个日期间相隔的周数
	 * @param startDate 开始日期 
	 * @param endDate 结束日期 
	 * @return yyyyWW[]
	 */
	public static String[] getYYYYWWWeeksFromTo(String fromDay, String toDay) {

		Date startDate = parse(fromDay, "yyyy-MM-dd");
		Date endDate = parse(toDay, "yyyy-MM-dd");

		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
		beginCalendar.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
		beginCalendar.setTime(startDate);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
		endCalendar.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
		endCalendar.setTime(endDate);

		StringBuffer weeksBf = new StringBuffer();
		if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
				&& beginCalendar.get(Calendar.MONTH) == endCalendar
						.get(Calendar.MONTH)
				//&& beginCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
				&& beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar
						.get(Calendar.WEEK_OF_YEAR)) {

			String weekIndex = findWeekIndex(beginCalendar.getTime());
			weeksBf.append(weekIndex + "|");
			return weeksBf.toString()
					.substring(0, weeksBf.toString().length() - 1).split("\\|");

		}

		while (beginCalendar.before(endCalendar)) {
			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环  
			if (beginCalendar.get(Calendar.YEAR) == endCalendar
					.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar
							.get(Calendar.MONTH)
					&& beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar
							.get(Calendar.WEEK_OF_YEAR)) {
				break;
			} else {

				String weekIndex = findWeekIndex(beginCalendar.getTime());
				weeksBf.append(weekIndex + "|");
				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
			}
		}

		String weekIndex = findWeekIndex(beginCalendar.getTime());
		weeksBf.append(weekIndex + "|");
		return weeksBf.toString().substring(0, weeksBf.toString().length() - 1)
				.split("\\|");
	}

	/**
	 * 计算两个日期间相隔的周数(跟手机周一样)
	 * @param startDate 开始日期 
	 * @param endDate 结束日期 
	 * @return yyyyWW[]
	 */
	/*public static String[] getYYYYWWWeeksFromTo(String fromDay, String toDay) {

		Date startDate = parse(fromDay, "yyyy-MM-dd");
		Date endDate = parse(toDay, "yyyy-MM-dd");
		
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
		beginCalendar.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
	    beginCalendar.setTime(startDate);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
		endCalendar.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
	    endCalendar.setTime(endDate);
		
		StringBuffer weeksBf = new StringBuffer();
		if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
				&& beginCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH)
				//&& beginCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
				&& beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar.get(Calendar.WEEK_OF_YEAR)
				) {
			int yearStr = beginCalendar.get(Calendar.YEAR);
			int weekOfYear = beginCalendar.get(Calendar.WEEK_OF_YEAR);
			String weekOfYearStr = "";
			if(weekOfYear<10){
				weekOfYearStr = "0"+weekOfYear;
			} else {
				weekOfYearStr = weekOfYear+"";
			}
			weeksBf.append(yearStr+weekOfYearStr+"|");			
			return weeksBf.toString().substring(0,weeksBf.toString().length()-1).split("\\|");
	    	
		}
		
		while (beginCalendar.before(endCalendar)) {
			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环  
			if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar.get(Calendar.MONTH)
					&& beginCalendar.get(Calendar.WEEK_OF_YEAR) == endCalendar.get(Calendar.WEEK_OF_YEAR)
					) {
				break;
			} else {
				int yearStr = beginCalendar.get(Calendar.YEAR);
				int weekOfYear = beginCalendar.get(Calendar.WEEK_OF_YEAR);
				String weekOfYearStr = "";
				if(weekOfYear<10){
					weekOfYearStr = "0"+weekOfYear;
				} else {
					weekOfYearStr = weekOfYear+"";
				}
				weeksBf.append(yearStr+weekOfYearStr+"|");
				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);				
			}
		}
		
		int endYearStr = endCalendar.get(Calendar.YEAR);
		int endWeekOfYear = endCalendar.get(Calendar.WEEK_OF_YEAR);
		String weekOfYearStr = "";
		if(endWeekOfYear<10){
			weekOfYearStr = "0"+endWeekOfYear;
		} else {
			weekOfYearStr = endWeekOfYear+"";
		}
		weeksBf.append(endYearStr+weekOfYearStr+"|");
		
		
		return weeksBf.toString().substring(0,weeksBf.toString().length()-1).split("\\|");
	}*/

	/**
	 * 获取日期是一年中的第几周
	 * @return
	 */
	/*    public static int getWeekOfYear(String day){
	    	int weekOfYear = -1;
	        try
	        {
	        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	            Date dt = format.parse(day);
	            Calendar calendar=Calendar.getInstance();
	            calendar.setTime(dt);
	            weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
	            //System.out.println("WEEK_OF_YEAR:"+calendar.get(Calendar.WEEK_OF_YEAR));
	        }
	        catch (ParseException e)
	        {
	        	e.printStackTrace();
	        }
	        return weekOfYear;
	    }*/

	public static Date getWeekend(Date curDate) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		if (curDay != Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - curDay);
			date = cal.getTime();
		} else {
			date = curDate;
		}
		return date;
	}

	/**
	 * 计算两个日期间相隔的周数 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期 
	 * @return 
	 */
	public static int computeWeek(Date startDate, Date endDate) {

		int weeks = 0;
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		while (beginCalendar.before(endCalendar)) {

			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环  
			if (beginCalendar.get(Calendar.YEAR) == endCalendar
					.get(Calendar.YEAR)
					&& beginCalendar.get(Calendar.MONTH) == endCalendar
							.get(Calendar.MONTH)
					&& beginCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCalendar
							.get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
				break;

			} else {
				beginCalendar.add(Calendar.DAY_OF_YEAR, 7);
				//System.out.println("week:"+beginCalendar.get(Calendar.WEEK_OF_YEAR));
				weeks += 1;
			}
		}
		return weeks;
	}

	/** 指定时间内的天数 **/
	public static int getDays(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return 0;
		Date sdate = parse(dateFrom, DATE);
		Date edate = parse(dateTo, DATE);
		return (int) ((edate.getTime() - sdate.getTime()) / 86400000);

	}

	public static int getMonths(String dateFrom, String dateTo) {
		if (CheckUtil.isEmpty(dateFrom) || CheckUtil.isEmpty(dateTo)
				|| CheckUtil.isNotDate(dateFrom)
				|| CheckUtil.isNotDate(dateTo))
			return 0;
		Date sdate = parse(dateFrom, "yyyy-MM");
		Date edate = parse(dateTo, "yyyy-MM");
		//int months = (int)((sdate.getTime() - edate.getTime())/(30* 24 * 60 * 60 * 1000));
		int months = getMonths(sdate, edate);
		return months;
	}

	/**   
	    * 计算两个日期之间相差的月数   
	    * @param date1   
	     * @param date2   
	     * @return   
	     */
	public static int getMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
					.get(Calendar.DAY_OF_MONTH))
				flag = 1;

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/** 加多少天后的日期 **/
	public static String getDateAddDays(String dateFrom, int day) {
		Date sdate = parse(dateFrom, DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdate);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH) + day);
		return format(calendar.getTime(), DATE);
	}

	/** 得到当天日期 **/
	public static String getCurrentDate(String dateFormat) {
		return format(new Date(), dateFormat);
	}

	/** 得到月初日期 **/
	public static String getMonthOfBegin(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return format(calendar.getTime(), dateFormat);
	}

	public static String getMonthOfBegin(String month, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		Date monthDate = parse(month, "yyyy-MM");
		calendar.setTime(monthDate);
		calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		return format(calendar.getTime(), dateFormat);
	}

	/** 得到月尾日期 **/
	public static String getMonthOfEnd(String month, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		Date monthDate = parse(month, "yyyy-MM");
		calendar.setTime(monthDate);
		calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天    
		calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		return format(calendar.getTime(), dateFormat);
	}

	/**
	 * 
	 * Title: getDateCurrentDate
	 * Description: 得到当天日期, 返回Date类型
	 * @param dateFormat
	 * @return date
	 */
	public static Date getDateCurrentDate(String dateFormat) {
		return changeDateFormat(new Date(), dateFormat, dateFormat);
	}

	/**
	 * 
	 * Title: getDateMonthOfBegin
	 * Description:	得到月初日期，返回Date类型
	 * @param dateFormat
	 * @return date
	 */
	public static Date getDateMonthOfBegin(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return changeDateFormat(calendar.getTime(), dateFormat, dateFormat);
	}

	/**
	 * 得到两个日期之间相差的天数
	 * @param start
	 * @param end
	 * @param format
	 * @return
	* @throws ParseException 
	 */
	public static int getDateSeparationDays(String start, String end,
			String format) {
		int days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			long sep = endDate.getTime() - startDate.getTime();
			days = (int) (sep / (1000 * 24 * 60 * 60));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	/*************************** Timestamps   *************************/

	public static String convertDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(date);
	}

	public static Date convertDate(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return parse(convertTimestamp(timestamp));
	}

	public static String convertTimestamp(Timestamp datetime, String format) {
		if (datetime == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(datetime);
	}

	public static String convertTimestamp(Timestamp datetime) {
		if (datetime == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(datetime);
	}

	public static Timestamp convertTimestamp(String str, String format) {
		if (str == null) {
			return null;
		}
		// string should be in yyyy-mm-dd hh:mm:ss.fffffffff format

		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return new Timestamp(df.parse(str).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(str
					+ " is not in valid Timestamp format.");
		}
	}

	/**
	 * convert a string to Timestamp
	 * 
	 * @param str the string to convert, should be in format yyyy-MM-dd HH:mm:ss
	 * @return converted Timestamp object, return null if str is null
	 * @throws IllegalArgumentException   if illegal string format
	 */
	public static Timestamp convertTimestamp(String str) {
		try {
			return (Timestamp) convertTimestamp(str, "yyyy-MM-dd HH:mm:ss");
		} catch (IllegalArgumentException e) {
		}

		// 再试一次
		try {
			return (Timestamp) convertTimestamp(str, "yyyy-MM-dd HH:mm");
		} catch (IllegalArgumentException e) {
		}

		// 再试一次
		try {
			return (Timestamp) convertTimestamp(str, "yyyy-MM-dd");
		} catch (IllegalArgumentException e) {
		}

		throw new IllegalArgumentException(str
				+ " not a valid Timestamp format.");
	}

	public static Timestamp getYesterday(Timestamp today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	/**
	 * 获得十六进制时间差，单位秒 
	 * @param time  指定的时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @return 当前时间和指定时间的时间差（秒）16进制
	 */
	public static String getTimeDifference(String time) {
		long between = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		java.util.Date end = null;
		java.util.Date begin = null;
		try {
			// 将截取到的时间字符串转化为时间格式的字符串
			java.util.Date date1 = (java.util.Date) sdf
					.parseObject("2008-01-01 00:00:00");
			String systemTime = sdf.format(date1).toString();

			end = sdf.parse(time);
			begin = sdf.parse(systemTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		between = Math.abs(end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		String betweenB = Long.toHexString(between);

		// 不足8位的时候前面补0
		int dif = 8 - betweenB.length();
		String difString = "";
		for (int i = 0; i < dif; i++) {
			difString += "0";
		}
		betweenB = difString + betweenB;

		return betweenB;
	}

	/**
	 * 反馈原始时间 
	 * @param vertime 十六进制的时间字符串
	 * @return 指定的时间，格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeEditon(String vertime) {
		long between = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// b转换成二进制
		long time = Long.parseLong(vertime, 16);

		java.util.Date end = null;
		java.util.Date begin = null;
		try {
			// 将截取到的时间字符串转化为时间格式的字符串
			java.util.Date date1 = (java.util.Date) sdf
					.parseObject("2008-01-01 00:00:00");
			String systemTime = sdf.format(date1).toString();

			begin = sdf.parse(systemTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		between = time * 1000 + Math.abs(begin.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(between);
		String enddate = sdf.format(cal.getTime());

		return enddate;
	}

	/**
	 * 获取周末日期周表计算规则
	 * @param curDate
	 * @return
	 */
	public static Date getWeekEnd(Date curDate) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		if (curDay != Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - curDay);
			date = cal.getTime();
		} else {
			date = curDate;
		}
		return date;
	}

	/**
	 * 获取日期是一年中的第几周
	 * @return
	 */
	public static int getWeekOfYear(String day) {
		int weekOfYear = -1;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = format.parse(day);

			//Calendar c = Calendar.getInstance();          
			//          Calendar c = new GregorianCalendar();
			//    		c.setFirstDayOfWeek(Calendar.SUNDAY);
			//    		c.setMinimalDaysInFirstWeek(7);

			Calendar c = Calendar.getInstance();
			c.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
			c.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
			c.setTime(dt);

			weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return weekOfYear;
	}

	/**
	* 取得当前日期是多少周
	* @param date
	* @return
	*/
	public static int getWeekOfYear(Date date) {

		//Calendar c = Calendar.getInstance();

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
		c.setMinimalDaysInFirstWeek(3);// 每年的第一周必须大于或等于3天，否则就算上一年的最后一周

		//      Calendar c = new GregorianCalendar();
		//		c.setFirstDayOfWeek(Calendar.MONDAY);
		//		c.setMinimalDaysInFirstWeek(7);

		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	* @NOTE:Java中的日期起始于1900年1月1日
	* 两年之间的交界处周数从1开始计算，即使12月31号是星期六，也归并到下一年的第1周中。
	*（这跟手机中不同，手机中采用类似的“四舍五入”处理，如果12月31号是 < 周四，归并到下一年的第1周，如果是 >= 周四，归并到本年的最后一周。）
	*/
	public static int getWeeksBetweenDates(Calendar dateFrom, Calendar dateTo) {
		dateFrom.setFirstDayOfWeek(Calendar.MONDAY);
		dateTo.setFirstDayOfWeek(Calendar.MONDAY);

		int yearFrom = dateFrom.get(Calendar.YEAR);
		int yearTo = dateTo.get(Calendar.YEAR);

		int weeksBetween = 0;
		//the year of dateFrom
		int weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR);
		if (weekFrom == 1 && dateFrom.get(Calendar.MONTH) == Calendar.DECEMBER)//腊月
		{
			dateFrom.set(yearFrom, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween -= dateFrom.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween -= weekFrom;
		//years between dates
		for (int i = 1; i <= yearTo - yearFrom; i++) {
			Calendar _lastDay = Calendar.getInstance();
			_lastDay.setFirstDayOfWeek(Calendar.MONDAY);
			_lastDay.set(yearFrom + i, 11, 25, 23, 59, 59);
			weeksBetween += _lastDay.get(Calendar.WEEK_OF_YEAR);
		}
		//the year of dateTo
		int weekTo = dateTo.get(Calendar.WEEK_OF_YEAR);
		if (weekTo == 1 && dateTo.get(Calendar.MONTH) == Calendar.DECEMBER)//腊月
		{
			dateTo.set(yearTo, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween += dateTo.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween += weekTo;

		return weeksBetween;
	}

	/**
	 * 自定义周计算规则
	 * @param args
	 */

	//YYYY+本周周索引
	public static String findWeekIndex(Date curDate) {
		String tableName = null;
		if (curDate != null) {
			tableName = fmtWeekend(curDate);
		} else {
			tableName = fmtWeekend(new Date());
		}
		return tableName;
	}

	//周表名 = ”MON_V“+fmtWeekend方法中返回的字符串
	public static String findWeekTable(Date curDate) {
		String tableName = null;
		if (curDate != null) {
			tableName = "MON_V" + fmtWeekend(curDate);
		}
		return tableName;
	}

	//YYYY+本周周索引
	public static String fmtWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		if (curDay != Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - curDay);
		}
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		String weekStr = (week > 9) ? String.valueOf(week) : "0" + week;
		formatStr = cal.get(Calendar.YEAR) + weekStr;
		return formatStr;
	}

	//YYYY+下周周索引
	public static String fmtNextWeekend(Date curDate) {
		String formatStr = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int curDay = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - curDay
				+ Calendar.SATURDAY);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		String weekStr = (week > 9) ? String.valueOf(week) : "0" + week;
		formatStr = cal.get(Calendar.YEAR) + weekStr;
		return formatStr;
	}

	public static String toYMDString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static Date toDate(String ymdhm) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return format.parse(ymdhm);
		} catch (ParseException e) {
			logger.error("", e);
			return null;
		}
	}

}
