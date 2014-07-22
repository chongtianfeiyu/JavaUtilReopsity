package com.yang.javalib.jquarzUtil;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.yang.javalib.jquarzUtil.job.TestJob;

public class ScheduleUtilTest {
	Scheduler scheduler;

	@Before
	public void setUp() throws Exception {
		  scheduler =ScheduleUtil.Scheduler();
		  
	}

	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * @throws InterruptedException 
	 * @throws SchedulerException 
	 * @throws ParseException 
	 * @Description: 周六测试 数据
	 * 
秒（0~59）
分钟（0~59）
小时（0~23）
天（月）（0~31，但是你需要考虑你月的天数）

月（0~11） 
天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）

7.年份（1970－2099） 
	 * void
	 * @throws
	 */
	@Test
	public void test() throws ParseException, SchedulerException, InterruptedException { 
		
		JobDetail jobDetail =ScheduleUtil.builderJob("test1", "test", TestJob.class);
		CronTrigger cronTrigger =ScheduleUtil.builderTrigger("test1", "test", "*/1 * * * 1,2,3,5,6,7 ?"); 
		 
		scheduler.scheduleJob(jobDetail, cronTrigger);  
	    scheduler.start(); 
	    
	    Thread.sleep(100000);
		
	}

}
