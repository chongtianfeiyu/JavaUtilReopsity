package com.yang.javalib.jquarzUtil;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.yang.javalib.baseUtil.date.DateUtil;

/**
 * @Description: 2.0 使用　调度任务管理类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-1 上午10:07:02 
 */
public class JobUtil2 {
	public static final int JOB_STOP = 0;
	public static final int JOB_RUN = 1;
	public static final int JOB_PAUSE = 2;
	public static final int JOB_INTERRUPUT = 3;
	public static final int JOB_RESUME = 4;
	/**
	 * @Fields : 调度器
	 */
	public static Scheduler scheduler = null;

	private static Log log = LogFactory.getLog(JobUtil.class);

	public static void init() {
		try {
			if (scheduler == null) {
				SchedulerFactory sf = new StdSchedulerFactory();
				scheduler = sf.getScheduler();
			}
			if (!(scheduler.isStarted()))
				scheduler.start();
		} catch (SchedulerException e) {
			log.error(e);
		}
	}
/*
	public static boolean addJob(TblAdminJob vo, boolean flag) {
		boolean b = false;
		try {
			IBusiness bo = SpringUtil.getInstance().getBusinessBO();
			if (flag) {
				bo.add(vo);
			}
			Class cl = Class.forName(vo.getTaskClass());

			//JobDetail job = new JobDetail();
			JobDetail job = ScheduleUtil.builderJob(vo.getJobName(),
					vo.getJobGroup(), cl);

				CronTrigger cTrigger = new CronTrigger(vo.getJobTrigger(),
						vo.getJobGroup(), vo.getJobName(), vo.getJobGroup(),
						vo.getCronExpression());
			CronTrigger cTrigger = ScheduleUtil.builderTrigger(vo.getJobName(),
					vo.getJobGroup(), vo.getCronExpression());

			Date ft = scheduler.scheduleJob(job, cTrigger);
			log.info("任务(" + vo.getJobName() + ")启动于:"
					+ DateUtil.getDateTime(ft));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}*/

	/*
		*//**
		* @Description: TODO
		* @param vo
		* @param flag
		* @param startTime
		* @param endTime
		* @return
		*/
	/*
	public static boolean addJob(TblAdminJob vo, boolean flag, Date startTime,
		Date endTime) {
	boolean b = false;
	try {
		IBusiness bo = SpringUtil.getInstance().getBusinessBO();
		if (flag) {
			bo.add(vo);
		}
		Class cl = Class.forName(vo.getTaskClass());
		JobDetail job = ScheduleUtil.builderJob(vo.getJobName(),
				vo.getJobGroup(), cl);
		CronTrigger cTrigger = new CronTrigger(vo.getJobTrigger(),
			vo.getJobGroup(), vo.getJobName(), vo.getJobGroup(),
			startTime, endTime, vo.getCronExpression());
		
		CronTrigger cTrigger = ScheduleUtil.builderTrigger(vo.getJobName(),
				vo.getJobGroup(), vo.getCronExpression());
	 
		Date ft = scheduler.scheduleJob(job, cTrigger);
		log.info("任务(" + vo.getJobName() + ")启动于:"
				+ DateUtil.getDateTime(ft));
		b = true;
	} catch (Exception ex) {
		log.error(ex);
	}

	return b;
	}*/
/*
	public static boolean deleteJob(TblAdminJob vo, boolean flag) {
		boolean b = false;
		try {
			IBusiness bo = SpringUtil.getInstance().getBusinessBO();
			if (flag) {
				bo.del(vo);
			}

			JobKey paramJobKey = new JobKey(vo.getJobName(), vo.getJobGroup());
			b = scheduler.deleteJob(paramJobKey);
			log.info("任务(" + vo.getJobName() + ")删除于:"
					+ DateUtil.getDateTime(new Date()));
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}*/
/*
	public static boolean updateJob(TblAdminJob vo, boolean flag) {
		boolean b = false;
		try {
			IBusiness bo = SpringUtil.getInstance().getBusinessBO();
			int state = vo.getJobState().intValue();
			int upStatus = state;
			if (state == 0) {
				b = stopJob(vo);
			} else if (state == 1) {
				b = startJob(vo);
			} else if (state == 2) {
				b = pauseJob(vo.getJobName(), vo.getJobGroup());
			} else if (state == 3) {
				b = interruptJob(vo.getJobName(), vo.getJobGroup());
			} else if (state == 4) {
				b = resumeJob(vo.getJobName(), vo.getJobGroup());
				upStatus = 1;
			}
			if (b) {
				if (flag) {
					vo.setJobState(Integer.valueOf(upStatus));
					bo.update(vo);
				}
				log.info("任务(" + vo.getJobName() + ")更新于:"
						+ DateUtil.getDateTime(new Date()));
			}
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}
*/
	public static boolean resumeJob(String jobName, String jobGroup) {
		boolean b = false;
		try {
			JobKey paramJobKey = new JobKey(jobName, jobGroup);
			scheduler.resumeJob(paramJobKey);
			log.info("任务(" + jobName + ")恢复于:"
					+ DateUtil.getDateTime(new Date()));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean pauseJob(String jobName, String jobGroup) {
		boolean b = false;
		try {
			JobKey paramJobKey = new JobKey(jobName, jobGroup);
			scheduler.pauseJob(paramJobKey);
			log.info("任务(" + jobName + ")暂停于:"
					+ DateUtil.getDateTime(new Date()));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean interruptJob(String jobName, String jobGroup) {
		boolean b = false;
		try {
			JobKey paramJobKey = new JobKey(jobName, jobGroup);
			b = scheduler.interrupt(paramJobKey);
			log.info("任务(" + jobName + ")中断于:"
					+ DateUtil.getDateTime(new Date()));
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean startJob(TblAdminJob vo) {
		boolean b = false;
		try {
			Class cl = Class.forName(vo.getTaskClass());
			JobDetail job = ScheduleUtil.builderJob(vo.getJobName(),
					vo.getJobGroup(), cl);

			CronTrigger cTrigger = ScheduleUtil.builderTrigger(vo.getJobName(),
					vo.getJobGroup(), vo.getCronExpression());
			/*
						CronTrigger cTrigger = new CronTrigger(vo.getJobTrigger(),
								vo.getJobGroup(), vo.getJobName(), vo.getJobGroup(),
								vo.getCronExpression());*/

			Date ft = scheduler.scheduleJob(job, cTrigger);
			log.info("任务(" + vo.getJobName() + ")启动于:"
					+ DateUtil.getDateTime(ft));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean startJob(TblAdminJob vo, Date startTime, Date endTime) {
		boolean b = false;
		try {
			Class cl = Class.forName(vo.getTaskClass());
			JobDetail job = ScheduleUtil.builderJob(vo.getJobName(),
					vo.getJobGroup(), cl);
			CronTrigger cTrigger = ScheduleUtil.builderTrigger(vo.getJobName(),
					vo.getJobGroup(), vo.getCronExpression());
			/*
						CronTrigger cTrigger = new CronTrigger(vo.getJobTrigger(),
								vo.getJobGroup(), vo.getJobName(), vo.getJobGroup(),
								startTime, endTime, vo.getCronExpression());*/

			Date ft = scheduler.scheduleJob(job, cTrigger);
			log.info("任务(" + vo.getJobName() + ")启动于:"
					+ DateUtil.getDateTime(ft));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean stopJob(TblAdminJob vo) {
		boolean b = false;
		try {
			JobKey paramJobKey = new JobKey(vo.getJobName(), vo.getJobGroup());

			b = scheduler.deleteJob(paramJobKey);
			log.info("任务(" + vo.getJobName() + ")删除于:"
					+ DateUtil.getDateTime(new Date()));
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	private static List getExecutingJobs() {
		List list = null;
		try {
			list = scheduler.getCurrentlyExecutingJobs();
			if ((list != null) && (list.size() > 0)) {
				JobDetail job;
				for (int i = 0; i < list.size(); ++i)
					job = (JobDetail) list.get(i);
			}
		} catch (Exception ex) {
			log.error(ex);
		}

		return list;
	}

	public static boolean pauseAllJob() {
		boolean b = false;
		try {
			scheduler.pauseAll();
			b = true;
			log.info("全部任务暂停于:" + DateUtil.getDateTime(new Date()));
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}

	public static boolean resumeAllJob() {
		boolean b = false;
		try {
			scheduler.resumeAll();
			scheduler.getMetaData();
			log.info("全部任务恢复于:" + DateUtil.getDateTime(new Date()));
			b = true;
		} catch (Exception ex) {
			log.error(ex);
		}

		return b;
	}
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.util.JobUtil
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/