package com.yang.javalib.jquarzUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 任务POJO
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-9 下午5:15:05 
 */
public class TblAdminJob implements Serializable {
	
	private Long id;
	
	/**
	 * @Fields : 任务名
	 */
	private String jobName;
	
	/**
	 * @Fields : TODO
	 */
	private String jobTrigger;
	
	/**
	 * @Fields : 任务分组
	 */
	private String jobGroup;
	
	/**
	 * @Fields : 任务ＩＤ，与业务有关，在报表中指调度任务
	 */
	private Long taskId;
	
	/**
	 * @Fields : 任务类型
	 */
	private Integer taskType;
	
	/**
	 * @Fields : 关联class. com.dc.bd.sop.report.job.ReportGenerateJob
	 */
	private String taskClass;
	
	/**
	 * @Fields : 任务状态
	 */
	private Integer jobState;
	
	/**
	 * @Fields : cron表达式
	 */
	private String cronExpression;
	
	private Date startTime;
	
	private Date stopTime;
	
	private Date createDate;
	
	private String sysCode;
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobTrigger() {
		return this.jobTrigger;
	}

	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskType() {
		return this.taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public Integer getJobState() {
		return this.jobState;
	}

	public void setJobState(Integer jobState) {
		this.jobState = jobState;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getSysCode() {
		return this.sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\MonitorWeb\WebContent\WEB-INF\lib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.vo.TblAdminJob
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/
