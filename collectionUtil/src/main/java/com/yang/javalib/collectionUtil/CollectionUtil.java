package com.yang.javalib.collectionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.yang.base.regular.RegUtil.RegUtil;
 

/**
 * @Description: 类集 工具类. 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-2 下午6:45:17 
 */
public class CollectionUtil {

	private static final Logger logger =Logger.getLogger(App.class);

	
	/**
	 * @Description: 对文件名排序. 从正则中抽出一个数字 ,按他进行排序,若找不到数字,则0
	 * @param fileNames
	 * @return
	 */
	public static List<String> sortFileNamesByNum(List<String> fileNames,String regularStr ){ 
   	 Collections.sort(fileNames, new FileNameCompare(regularStr)); 
   	 return fileNames ;
	} 
	
	/**
	 * @Description:  对文件名排序. 从正则中抽出一个数字 ,按他进行排序,若找不到数字,则0
	 * @param fileNames
	 * @param regularStr
	 * @return
	 */
	public static String[] sortFileNamesByNum(String[] fileNames,String regularStr ){  
		Arrays.sort(fileNames, new FileNameCompare(regularStr));  
   	 return fileNames ;
	}
	
	
}


/**
 * @Description: 文件名对比器
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-5-2 下午6:45:30 
 */
class  FileNameCompare implements Comparator<String>{
 
	/**
	 * @Fields : 正则表达式,从name 中提取数字 ,
	 */
	String regularStr;
    Pattern p ;
	  
	public FileNameCompare(String regularStr) {
		super();
		this.regularStr = regularStr;
        p = Pattern.compile(regularStr); 
	} 

	public int compare(String o1, String o2) {  
		
		
		
		return getNum(o1)-getNum(o2);
	}
	
	private int getNum(String fileName) {
		
		String resultNum = RegUtil.getMatchStr(fileName, p, 1);
		 //System.out.println(fileName +"  提取出的数字"+resultNum);
		if (StringUtils.isBlank(resultNum) || !NumberUtils.isNumber(resultNum))
			return 0;
		else {
			return Integer.parseInt(resultNum);
		}

	}
 
}

