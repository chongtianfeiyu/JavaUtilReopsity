package com.yang.javalib.dbutil.common;

public class DBConnectConfig {
	private String driverName;
	private String username;
	private String url;
	private String password;

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public String getUsername() {
		return this.username;
	}

	public String getUrl() {
		return this.url;
	}

	public String getPassword() {
		return this.password;
	}
}

/* Location:           F:\myProject\WorkProject\jiankong2\workBench\common\sopLib\BDBase.jar
* Qualified Name:     com.dc.bd.frame.base.vo.DBConnectConfig
* Java Class Version: 5 (49.0)
* JD-Core Version:    0.5.3
*/