package com.yang.javalib.baseUtil.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil { 
	

    /**
     * @Description: 把时间格式转换为字符串
     * @param date 时间
     * @param format 时间格式
     * @return
     * String
     * @throws
     */
    public static String formatDate2Str(java.util.Date date, EDateFormat format) {
        if (date==null || format==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.dateFormatStr);
        return sdf.format(date).toString();
    }

    public static String formatDate2Str(java.sql.Timestamp date, EDateFormat format) {
        if (date==null || format==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.dateFormatStr);
        return sdf.format(date).toString();
    }
    
 
    /**
     * @Description: 把时间字体符串转换为时间Date
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     * Date
     * @throws
     */
    public static Date formatStr2Date(String dateStr,EDateFormat format) throws ParseException{
        return new SimpleDateFormat(format.dateFormatStr).parse(dateStr);
    }
    
    
	/**
     * 将java日期，转换成字符串
     * @param date
     * @param format  
     * replace by DateUtil.formatDate2Str"
     * @return 
     */
    @Deprecated
    public static String formatJavaDateToString(java.util.Date date, String format) {
        if (date==null || format==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date).toString();
    }
    /**
     * @Description: 把时间字条串按指定格式转化为时间 
     * @param dateStr   FG:2014-04-30 17:50:33.257
     * @param format   FG: yyyy-MM-dd HH:mm:ss.fff
     * @return
     * @throws ParseException
     */
    @Deprecated
    public static Date formatStr2DateByFormat(String dateStr,String format) throws ParseException{
    	
        return new SimpleDateFormat(format).parse(dateStr);
    	
    }
    
    /**
     * @Description:  把时间转换为YYMMDDHHMISSS格式的字符串
     * @param time
     * @return
     * String
     * @throws
     */
    @Deprecated
    public static String formatDbDateToYYMMDDHHMISSStr(java.sql.Timestamp time) { 
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }
    
    /**
     * @Description: 把时间转换为字符串
     * @param time
     * @return
     * String
     * @throws
     */
    @Deprecated
    public static String formatDbDateToStr(java.sql.Timestamp time) { 
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        return df.format(time);
    }

    @Deprecated
    public static String formatDbDateToString(java.sql.Timestamp time) { 
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
        return df.format(time);
    }

    @Deprecated
    public static String formatDateToString(Date date) { 
    	DateFormat df = new SimpleDateFormat("yyyyMMdd"); 
        return df.format(date);
    }

    @Deprecated
    public static Date formatStrToDate(String dateString) { 
    	DateFormat df = new SimpleDateFormat("yyyyMMdd"); 
    	Date date = null;
    	try {
    		date = df.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return date;
    }

    @Deprecated
    public static Date formatDateToStr(String dateString) { 
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date date = null;
    	try {
    		date = df.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return date;
    }
    
    /**
     * 根据java日期，转换成DataBase日期
     * @param date
     * @return
     */
    public static Timestamp formatJavaDateToDB(java.util.Date date){
    	return new java.sql.Timestamp(date.getTime());
    }
    
    /**
     * 根据已知日期，加上或减去多少月，得到新月份
     * @param date
     * @param days
     * @return
     */
    public static Date updateMonth(java.util.Date date, int days) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, days);
    	return cal.getTime();
    }
    /**
     * 根据已知日期，加上或减去多少天后，得到新日
     * @param date
     * @param days
     * @return
     */
    public static Date updateDay(java.util.Date date, int days) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, days);
    	return cal.getTime();
    }
    
    /**
     * @return 获取当前时间戳
     */
    public static Timestamp getCurrentTime(){
		return new Timestamp(System.currentTimeMillis());
	}
    
    /**
    * @Description: 获取日期的前一天
    * @param specifiedDay
    * @return
    * @return String    返回类型
    * @author chenqiang
    * @date 2013-11-22 下午3:30:17
    */
    public static String getBeforeDay(String paraDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(paraDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);
        String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    } 
    
    public static String getCurrDate() {
    	SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(new Date());
    }
    
    public static Timestamp getToUnionTime(String createTime){
		StringBuilder timeBuilder = new StringBuilder();
		timeBuilder.append(createTime.substring(0, 4));
		timeBuilder.append('-');
		timeBuilder.append(createTime.substring(4, 6));
		timeBuilder.append('-');
		timeBuilder.append(createTime.substring(6, 8));
		timeBuilder.append(' ');
		timeBuilder.append(createTime.substring(8, 10));
		timeBuilder.append(':');
		timeBuilder.append(createTime.substring(10, 12));
		timeBuilder.append(':');
		timeBuilder.append(createTime.substring(12, 14));
		Date createDate = null;
		try
		{
			createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeBuilder.toString());
		}
		catch (ParseException pe)
		{
			pe.printStackTrace();
		}
		Timestamp toUnionTime = formatJavaDateToDB(createDate);
		return toUnionTime;
    }
    

    @Deprecated
	public String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

    @Deprecated
	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

    @Deprecated
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				DateFormat df = new SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

    @Deprecated
	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

    @Deprecated
	public static String format1(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static int getYear(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(1);
	}

	public static int getMonth(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return (c.get(2) + 1);
	}

	public static int getDay(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(5);
	}

	public static int getHour(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(11);
	}

	public static int getMinute(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(12);
	}

	public static int getSecond(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(13);
	}

	public static long getMillis(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static int getWeek(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(7);
		dayOfWeek -= 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

    @Deprecated
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

    @Deprecated
	public static String getDate(java.util.Date date, String formatStr) {
		return format(date, formatStr);
	}

    @Deprecated
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

    @Deprecated
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static java.util.Date addDate(java.util.Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
		return c.getTime();
	}

	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / 86400000L);
	}

    @Deprecated
	public static java.util.Date getdate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

    @Deprecated
	public static java.util.Date getdate1(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	/** 
	* 获得当前日期的前后几天日期	 */
	private String getDate(int diff) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, diff);
		Date newDate = calendar.getTime();
		return sdf.format(newDate);
	}
 

	/** 
	* 获得当月的最后一天	 */
	public String getLastDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		int lastDay = c.get(Calendar.DATE);
		return Integer.toString(lastDay);
	}

	
	

    
//    public static void main(String[] args){
//    	MyDateUtil myDateUtil =  new MyDateUtil();
//    	String ssString = myDateUtil.formatDbDateToString(MyDateUtil.getCurrentTime());
//    	System.out.println(">>>>>>>>>>>>>"+ssString);
//    }
}
