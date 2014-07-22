package com.yang.javalib.jquarzUtil;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description: 调度工具　２.０使用
 * @author yangxiaodon
 * 
 
 
 Quartz 定义了两种 基本接口 Job 和 Trigger . 
     看名字也就知道，我们的任务必须实现 Job， 
      我们的时间触发器定义在 Trigger 内

定义 了 JOB 类后，需要定一个Scheduler类用来执行计划任务。
一个JobDetail 类来描述这个任务的信息，包括任务信息，任务所在组，任务执行的类。
然后还要定义一个 触发器，类似的也包括触发器名，触发器所在组，触发器触发时间设定。
最后是调度器Scheduler类执行计划任务。基本上一个计划任务执行的流程就完成了。
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-9 下午2:46:02 
 */
public class ScheduleUtil {

	private static final Logger log = Logger.getLogger(ScheduleUtil.class);

	/**
	 * @Description: TODO
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * @Description: 从调度工厂得到调度器
	
	注意, 要调用 .start()方法, 才会开始. 否则,不会有任务任务开始调用
	    // Start up the scheduler (nothing can actually run until the 
	    // scheduler has been started)
	    sched.start();
	    
	 * @return
	 * @throws SchedulerException
	 */
	public static Scheduler Scheduler() throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		return sched;
	}

	/**
	 * @Description: 关闭　Scheduler
	 * @param scheduler
	 * @throws SchedulerException
	 */
	public static void shutDownScheduler(Scheduler scheduler)
			throws SchedulerException {
		scheduler.shutdown(true);
		SchedulerMetaData metaData = scheduler.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}

	/**
	 * @Description: 得到时间调度器
	 * @return
	 */
	public Timer getJDKTimer() {
		//timer.schedule(task, date, 10000);
		Timer timer = new Timer();
		return timer;
	}

	/**
	 * @Description:根据  任务名, 任务分组,建造一个任务.
	 * @param name
	 * @param group
	 * @return
	 */
	public static JobDetail builderJob(String name, String group, Class _class) {
	 
		JobDetail job = newJob(_class).withIdentity(name, group).build();
		/*JobDetail job = new newJob(HelloJob.class).withIdentity("job1",
				"group1").build();*/
		return job;
	}

	/**
	 * @Description:  
	 * @param triggerName  
	 * @param group   
	 * @param cronExpression  "0/20 * * * * ?"
	 * @return
	 * @throws ParseException
	 */
	public static CronTrigger builderTrigger(String triggerName, String group,
			String cronExpression) throws ParseException {

		CronTrigger trigger = newTrigger().withIdentity(triggerName, group)
				.withSchedule(cronSchedule(cronExpression)).build();

		return trigger;
	}

	/**
	 * @Description: 建立简单的调度.
	 * @param triggerName
	 * @param group
	 * @param startTime
	 * @return
	 */
	public static SimpleTrigger builderSimpleTrigger(String triggerName,
			String group, Date startTime, int intervalInSeconds, int repeatCount) {

		SimpleTrigger simpleTrigger = newTrigger()
				.withIdentity(triggerName, group)
				.startAt(startTime)
				//开始时间 
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(
								intervalInSeconds) //间隔.
								.withRepeatCount(repeatCount))//重复资料
				.build();

		return simpleTrigger;
	}

}

/**
 * @Description: 这是一个例子.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-9 下午3:14:12 
 */
class HelloJob implements Job {

	private static Logger log = Logger.getLogger(HelloJob.class);

	/**
	 * <p>
	 * Empty constructor for job initilization
	 * </p>
	 * <p>
	 * Quartz requires a public empty constructor so that the
	 * scheduler can instantiate the class whenever it needs.
	 * </p>
	 */
	public HelloJob() {
	}

	/**
	 * <p>
	 * Called by the <code>{@link org.quartz.Scheduler}</code> when a
	 * <code>{@link org.quartz.Trigger}</code> fires that is associated with
	 * the <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// Say Hello to the World and display the date/time

		log.info("Hello World! - " + new Date());
	}

}
