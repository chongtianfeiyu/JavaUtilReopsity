package com.yang.javalib.xmlUtil.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Description: 这种读配置文件 的方式挺好的.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-27 下午2:48:10 
 */
public class Constants {

	private static final Logger log = Logger.getLogger(Constants.class);
	public static final String PROPERTIES_NAME = "task.properties";
	public static final String SYSTEM_LOG = "system"; // 系统管理及参数配置
	public static final String BUSINESS_LOG = "business"; // 任务分配及处理,主机监控
	public static final int LOG_TYPE_SYSTEM = 1; // 系统管理日志
	public static final int LOG_TYPE_PARAM = 2; // 参数配置日志
	public static final int LOG_TYPE_TASK = 3; // 任务分配与处理日志
	public static final int LOG_TYPE_INSPECT = 4; // 主机监控日志

	public static String REPORT_BASE_PATH = "reportPath"; // 报表基本路径
	public static String TASK_BASE_PATH = "taskPath"; // 任务基本路径
	public static String DEFAULT_BASE_PATH = "defaultPath"; // 公告基本路径

	public static String SOCKET_IP = "socketIp"; // 交易系统参数配置socket IP
	public static String SOCKET_PORT = "socketPort"; // 交易系统参数配置socket 端口

	public static String SYSLOG_INFO = "Daemon.Notice";
	public static String SYSLOG_WARNING = "Daemon.Warning";
	public static String SYSLOG_ERROR = "Daemon.Error";

	static {
		Properties pro = Constants.readPropertyFile(PROPERTIES_NAME);
		REPORT_BASE_PATH = pro.getProperty(REPORT_BASE_PATH);
		TASK_BASE_PATH = pro.getProperty(TASK_BASE_PATH);
		DEFAULT_BASE_PATH = pro.getProperty(DEFAULT_BASE_PATH);
		SOCKET_IP = pro.getProperty(SOCKET_IP);
		SOCKET_PORT = pro.getProperty(SOCKET_PORT);
	}

	@SuppressWarnings("deprecation")
	public static Properties readPropertyFile(String fileName) {
		InputStream input = null;
		Properties prop = new Properties();
		try {
			fileName = Constants.class.getClassLoader().getResource(fileName)
					.getPath();
			fileName = java.net.URLDecoder.decode(fileName);
			File file = new File(fileName);
			input = new FileInputStream(file);
			prop.load(input);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				input.close();
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
		}
		return prop;
	}

	public static void main(String[] arg) {
		String str = Constants.class.getClassLoader()
				.getResource(PROPERTIES_NAME).getPath();
		System.out.println(str);
	}
}
