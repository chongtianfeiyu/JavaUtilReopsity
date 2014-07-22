package com.yang.javalib.threadUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yang.javalib.xmlUtil.config.ConfigUtil;

/**
 * @Description	: 线程池工具类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-26 下午1:08:52 
 */
public class ThreadPoolUtil {
	private static Log log = LogFactory.getLog(ThreadPoolUtil.class);

	private static ThreadPoolUtil pool = null;
	private static ThreadPoolExecutor threadPool = null;
	private int corePoolSize = 10;
	private int maximumPoolSize = 50;
	private int keepAliveTime = 2;
	private int threadQueueSize = 50000;

  private ThreadPoolUtil() {
    if (ConfigUtil.getConfigValue("ThreadPool", "corePoolSize").length() > 0) {
      this.corePoolSize = Integer.parseInt(ConfigUtil.getConfigValue("ThreadPool", "corePoolSize"));
    }
    if (ConfigUtil.getConfigValue("ThreadPool", "maximumPoolSize").length() > 0) {
      this.maximumPoolSize = Integer.parseInt(ConfigUtil.getConfigValue("ThreadPool", "maximumPoolSize"));
    }
    if (ConfigUtil.getConfigValue("ThreadPool", "keepAliveTime").length() > 0) {
      this.keepAliveTime = Integer.parseInt(ConfigUtil.getConfigValue("ThreadPool", "keepAliveTime"));
    }
    if (ConfigUtil.getConfigValue("ThreadPool", "threadQueueSize").length() > 0) {
      this.threadQueueSize = Integer.parseInt(ConfigUtil.getConfigValue("ThreadPool", "threadQueueSize"));
    }
    threadPool = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue(this.threadQueueSize), new ThreadPoolExecutor.DiscardOldestPolicy());
  }

  public static synchronized ThreadPoolUtil getInstance()
  {
    try
    {
      if (pool == null)
        pool = new ThreadPoolUtil();
    }
    catch (Exception ex) {
      log.error("", ex);
    }
    return pool;
  }

  public void add(Runnable run) {
    try {
      threadPool.execute(run);
      log.debug("活动线程数：" + threadPool.getActiveCount());
      log.debug("活动池大小：" + threadPool.getPoolSize());
      log.debug("完成的任务数：" + threadPool.getCompletedTaskCount());
    } catch (Exception ex) {
      log.error("", ex);
    }
  }
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.ThreadPoolUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/