package com.yang.javalib.xmlUtil.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Description: 读取Properties配置文件　中的内容
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-19 下午5:42:31 
 */
public class PropertiesUtil {
	static final Logger log = Logger.getLogger(PropertiesUtil.class);

	private Properties props;
	private String fileName;

	public PropertiesUtil(String fileName) {
		this.fileName = fileName;
		readProperties(fileName);
	}

	/**
	 * @Description: 加载Properties文件
	 * @param fileName
	 */
	private void readProperties(String fileName) {
		try {
			props = new Properties();
			FileInputStream fis = new FileInputStream(fileName);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取某个属性
	 */
	public String getProperty(String key) {
		return props.getProperty(key).trim();
	}

	/**
	 * @Description: 返回 回boolean值, 
	 *  true 或 1  代表真. 其余都为假
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		String temp = getProperty(key);
		return "true".equalsIgnoreCase(temp) || "1".equalsIgnoreCase(temp);
	}

	/**
	 * @Description: TODO
	 * @param key
	 * @return
	 */
	public int getInt(String key) { 
		return Integer.parseInt(getProperty(key));
	}

	/**
	 * 获取所有属性，返回一个map,不常用
	 * 可以试试props.putAll(t)
	 */
	public Map getAllProperty() {
		Map map = new HashMap();
		Enumeration enu = props.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = props.getProperty(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 在控制台上打印出所有属性，调试时用。
	 */
	public void printProperties() {
		props.list(System.out);
	}

	/**
	 * 写入properties信息
	 */
	public void writeProperties(String key, String value) {
		try {
			OutputStream fos = new FileOutputStream(fileName);
			props.setProperty(key, value);
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, "『comments』Update key：" + key);
		} catch (IOException e) {
		}
	}

	public static void main(String[] args) {
		PropertiesUtil util = new PropertiesUtil("conf/config.properties");
		System.out.println("ip=" + util.getProperty("ip"));
		util.writeProperties("key", "value0");
	}

}
