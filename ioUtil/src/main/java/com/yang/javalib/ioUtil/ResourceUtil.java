package com.yang.javalib.ioUtil;

import java.net.URL;

/**
 * @Description	: 寻找资源文件
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-27 下午12:57:42 
 */
public class ResourceUtil {
	
	 
	/**
	 * @Description: 得到包下的资源文件
	 * @param resoucePackagePath 文件在包中的路径 .(classpath)
	 * @return
	 * String
	 * @throws
	 */
	public static String getResourcePath(String resoucePackagePath){
		String path = ResourceUtil.class.getResource (resoucePackagePath).getFile() ;
		return path ;
	}
	
	public static URL getResourceURL(String resoucePackagePath){
		URL path = ResourceUtil.class.getResource (resoucePackagePath) ;
		return path ;
	}

}
