package com.yang.javalib.json.dto;

import java.util.List;

/**
 * @Description: 即一次请求得到的任务分组
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-19 下午9:01:59 
 */
public class TaskGroupDTO {
	List<TaskDTO> aaData;
	int itotalRecords;
	int itotalDisplayRecords;
	String secho;
	public List<TaskDTO> getAaData() {
		return aaData;
	}
	public void setAaData(List<TaskDTO> aaData) {
		this.aaData = aaData;
	}
	public int getItotalRecords() {
		return itotalRecords;
	}
	public void setItotalRecords(int itotalRecords) {
		this.itotalRecords = itotalRecords;
	}
	public int getItotalDisplayRecords() {
		return itotalDisplayRecords;
	}
	public void setItotalDisplayRecords(int itotalDisplayRecords) {
		this.itotalDisplayRecords = itotalDisplayRecords;
	}
	public String getSecho() {
		return secho;
	}
	public void setSecho(String secho) {
		this.secho = secho;
	}
	
}
