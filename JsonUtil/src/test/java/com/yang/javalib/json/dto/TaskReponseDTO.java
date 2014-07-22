package com.yang.javalib.json.dto;

/**
 * @Description: 一次请求的返回值 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-19 下午9:03:18 
 */
public class TaskReponseDTO {
	
	String message;
	TaskGroupDTO data;
	boolean success;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TaskGroupDTO getData() {
		return data;
	}
	public void setData(TaskGroupDTO data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
