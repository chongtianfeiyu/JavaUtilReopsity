package com.yang.base.web;

/**
 * 封装 http 监控的结果。
 * @author zhangrongc
 */
public class HttpResponseVO {

	private String content;
	private int statusCode;
	private long responseTime;

	public HttpResponseVO() {
	}

	public HttpResponseVO(String content, int statusCode, long responseTime) {
		this.content = content;
		this.statusCode = statusCode;
		this.responseTime = responseTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "HttpResponseVO [content=" + content + ", statusCode="
				+ statusCode + ", responseTime=" + responseTime + "]";
	}

}
