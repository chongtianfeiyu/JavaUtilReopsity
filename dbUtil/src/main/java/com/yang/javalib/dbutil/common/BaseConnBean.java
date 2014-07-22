package com.yang.javalib.dbutil.common;

public class BaseConnBean {

	private String name; // 这一个是.pool的name .
	private String username;
	private String password;
	private String url;
	private String driver;

	private int initialSize;
	private int minIdle;
	private int maxActive;
	private long maxWait;

	public BaseConnBean(String name, String driver, String jdbcurl,
			String username, String password) {
		this.name = name;
		this.driver = driver;
		this.url = jdbcurl;
		this.username = username;
		this.password = password;
		this.maxActive = 10; //设置 10个.
		this.maxWait = 5000;
		this.initialSize = 2;
		this.minIdle = 2;
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public String getDriverClassName() {
		return driver;
	}

	public String getDriver() {
		return driver;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + initialSize;
		result = prime * result + maxActive;
		result = prime * result + (int) (maxWait ^ (maxWait >>> 32));
		result = prime * result + minIdle;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseConnBean other = (BaseConnBean) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (initialSize != other.initialSize)
			return false;
		if (maxActive != other.maxActive)
			return false;
		if (maxWait != other.maxWait)
			return false;
		if (minIdle != other.minIdle)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
