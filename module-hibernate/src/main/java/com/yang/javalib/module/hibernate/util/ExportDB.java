package com.yang.javalib.module.hibernate.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * @Description: 根据hibernate配置文件在数据库生成相应的信息
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-31 下午1:49:48 
 */
public class ExportDB {

	public static void main(String[] args) {
		
		//读取hibernate.cfg.xml文件 //默认的配置文件
		Configuration cfg = new Configuration().configure();
		
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true, true);
	}
}
