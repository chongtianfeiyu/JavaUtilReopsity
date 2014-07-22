package com.yang.javalib.threadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * @Description	: ExecutorService 工具类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-26 下午3:54:46 
 */
public class ExecutorServiceUtil {
	
	private static final Logger log =Logger.getLogger(ExecutorServiceUtil.class) ;

	private static ExecutorServiceUtil executorServiceUtil = null;
 
	private static ExecutorService executorService = null;
	 
	private ExecutorServiceUtil()
	{ 
		
		executorService=Executors.newScheduledThreadPool(5);
		//可以添加配置文件, 根据配置添加信息. 
	    /*if (ConfigUtil.getConfigValue("ThreadPool", "corePoolSize").length() > 0) {
		      this.corePoolSize = Integer.parseInt(ConfigUtil.getConfigValue("ThreadPool", "corePoolSize"));
		}*/
	}
	

	  public static synchronized ExecutorServiceUtil getInstance()
	  {
	    try
	    {
	      if (executorServiceUtil == null)
	    	  executorServiceUtil = new ExecutorServiceUtil();
	    }
	    catch (Exception ex) {
	      log.error("", ex);
	    }
	    return executorServiceUtil;
	  }

	
	
	

}
