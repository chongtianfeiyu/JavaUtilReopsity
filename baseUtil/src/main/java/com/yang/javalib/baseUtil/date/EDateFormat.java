package com.yang.javalib.baseUtil.date;

public enum EDateFormat {
	
	yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"), //2014-04-30 17:50:33
	yyyyMMddHHmmss("yyyyMMddHHmmss"), //2014-04-30 17:50:33
	yyyyMM("yyyyMM"), //2014-04-30 17:50:33
	yyyy_MM_dd("yyyy-MM-dd"), //2014-04-30 17:50:33
	yyyy_MM_dd1("yyyy/MM/dd"), //2014-04-30 17:50:33
	 
	yyyyMMdd("yyyyMMdd"), //2014-04-30 17:50:33
	HH_mm_ss("HH:mm:ss"), //2014-04-30 17:50:33
	yyyy_MM_dd_HH_mm_ss_fff("yyyy-MM-dd HH:mm:ss.fff") //2014-04-30 17:50:33.257
	
	;
	
	public String dateFormatStr ;

	private EDateFormat(String dateFormatStr) {
		this.dateFormatStr = dateFormatStr;
	}
	
	
}
