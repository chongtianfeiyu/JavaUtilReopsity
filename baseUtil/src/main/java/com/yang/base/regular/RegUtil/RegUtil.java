package com.yang.base.regular.RegUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
 

/**
 * @Description: 正则表达式工具类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-28 下午2:18:17 
 */
public class RegUtil {

	private static final Logger log = Logger.getLogger(RegUtil.class);

	public static void main(String[] args) {
		try {
			RegUtil.getLogDateStyleStr("resourceDir/yyyy-MM-dd.log");
			RegUtil.getLogDateStyleStr("resourceDir/YYYY:MM:DD.log");
			RegUtil.getLogDateStyleStr("resourceDir/YYYY_MM_DD.log");
			RegUtil.getLogDateStyleStr("resourceDir/YYYYMM.log");
   //DateUtil.format(new Date(), format)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 转换date类型的日志的格式串
	 * @param logPath
	 * @return
	 */
	public static String getLogDateStyleStr(String logPath) {
		String temp = getMatchStr(logPath, "y{2,4}.{0,2}M{1,2}.{0,2}d{1,2}");

		if (StringUtils.isBlank(temp)) //如果年月日没有,则取年月的.
			temp = getMatchStr(logPath, "y{2,4}.{0,2}M{1,2}");

		if (StringUtils.isNotBlank(temp))
			log.info("从" + logPath + "中解析出时间格式串:" + temp);
		else {
			log.info("从" + logPath + "中解析出时间格式串:失败");
		}
		return temp;
	}

	/**
	 * @Description: 从内容中得到匹配的子串
	 * @param content
	 * @param regex
	 * @return
	 */
	public static String getMatchStr(String content, String regex) {
		Pattern p = Pattern.compile(regex);
		return getMatchStr(content, p);
	}

	/**
	 * @Description: 从内容中得到匹配的子串
	 * @param content
	 * @param p
	 * @return
	 */
	public static String getMatchStr(String content, Pattern p) {
		return getMatchStr(content, p, 0);
	}

	/**
	 * @Description: 从内容中得到匹配的子串的某个分组
	 * @param content
	 * @param p
	 * @param i
	 * @return
	 */
	public static String getMatchStr(String content, String regex, int i) {
		Pattern p = Pattern.compile(regex);
		return getMatchStr(content, p, i);
	}

	/**
	 * @Description: 从内容中得到匹配的子串的某个分组
	 * @param content
	 * @param p
	 * @param i 1代表第一个,   0代表匹配 的
	 * @return
	 */
	public static String getMatchStr(String content, Pattern p, int i) {
		Matcher m = p.matcher(content);
		if (m.find()) {
			return m.group(i);
		}
		return "";
	}

	/**
	 * @Description: 从内容中得到匹配的子串的某个分组,选中多个分组组成一串
	 * @param content
	 * @param p
	 * @param groups  i 1代表第一个,   0代表匹配 的
	 * @return
	 */
	public static String getMatchGroupAsStr(String content, Pattern p, int... groups) {

		Matcher m = p.matcher(content);
		if (m.find()) {
			String temp = "";
			for (int jItem : groups) {
				temp += m.group(jItem);
			}
			return temp;

		}
		return "";
	}


	public static List<String> getMatchAsArray(String content, Pattern p) {
		if(StringUtils.isBlank(content))
			return null;
		
		List<String > results =new ArrayList<String>();
		 
		Matcher m = p.matcher(content);
		while(m.find()) {  
			 results.add(m.group(0)); 
		}
		return results;
	}

 	

	/**
	 * @Description: 得到多个匹配的作为Sring
	 * @param content
	 * @param pattern
	 * @return
	 */
	public static List<String> getMatchAsArray(String content, String pattern) {
		
		if(StringUtils.isBlank(content))
			return null;
		return getMatchAsArray(content,Pattern.compile(pattern)); 
	}

	/**
	 * @Description: TODO 带分组的
	 * @param content
	 * @param p
	 * @param groups   1代表第一个,   0代表匹配 的
	 * @return
	 */
	public static String[] getMatchGroupAsArray(String content, Pattern p, int... groups) {
		if(StringUtils.isBlank(content))
			return null;
		
		String[] result =new 	String[groups.length] ;
		Matcher m = p.matcher(content);
		if (m.find()) { 
			for (int i = 0; i < groups.length; i++) {
				result[i] = m.group(groups[i]);
			}
			return result;
		}
		return null;
	}

 	

	public static String[] getMatchGroupAsArray(String content, String pattern, int... groups) {
		
		if(StringUtils.isBlank(content))
			return null;
		return getMatchGroupAsArray(content,Pattern.compile(pattern),groups); 
	}
}
