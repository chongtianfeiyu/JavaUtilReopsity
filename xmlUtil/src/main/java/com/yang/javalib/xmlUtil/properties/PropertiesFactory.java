package com.yang.javalib.xmlUtil.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * @Description: TODO
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-14 上午10:38:31 
 */
public class PropertiesFactory {

	/**
	 * @Description: 从指定文件 中, 得到 Properties
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Properties readPropertyFile(String fileName) {
	    InputStream input = null;
	    Properties prop = new Properties();
	    try {
	        /*fileName = PropertiesUtil.class.getClassLoader().getResource(fileName).
	                   getPath();*/
	        fileName = java.net.URLDecoder.decode(fileName);
	        File file = new File(fileName);
	        input = new FileInputStream(file);
	        prop.load(input);
	    } catch (FileNotFoundException e) {
	        PropertiesUtil.log.error(e.getMessage());
	    } catch (IOException e) {
	        PropertiesUtil.log.error(e.getMessage());
	    } finally {
	        try {
	            input.close();
	        } catch (IOException ex) {
	            PropertiesUtil.log.error(ex.getMessage());
	        }
	    }
	    return prop;
	}

}
