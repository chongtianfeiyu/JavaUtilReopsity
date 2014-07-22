package com.yang.javalib.jquarzUtil.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job{
	
	private static final Logger log =Logger.getLogger(TestJob.class);

	private static Integer count =0 ;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		log.info("调用 "+(count++)+"次."); 
	}

}
