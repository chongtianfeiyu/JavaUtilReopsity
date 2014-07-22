package com.yang.base.resoucebundle;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @Description	: 国际化资源绑定UTIL
 * http://lavasoft.blog.51cto.com/62575/184605
 * 这个类提供软件国际化的捷径。通过此类，可以使您所编写的程序可以：
         轻松地本地化或翻译成不同的语言
         一次处理多个语言环境
         以后可以轻松地进行修改，支持更多的语言环境
 
说的简单点，这个类的作用就是读取资源属性文件（properties），然后根据.properties文件的名称信息（本地化信息），匹配当前系统的国别语言信息（也可以程序指定），然后获取相应的properties文件的内容。
 
使用这个类，要注意的一点是，这个properties文件的名字是有规范的：一般的命名规范是： 自定义名_语言代码_国别代码.properties，
如果是默认的，直接写为：自定义名.properties
比如：
myres_en_US.properties
myres_zh_CN.properties
myres.properties
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-4-2 下午4:28:46 
 */
public class ResourceBundleUtil {
	
	ResourceBundle resourceBundle;
	
	/**
	 * @Description: TODO
	 * @param baseName  com.yang.base.resoucebundle.myres 对应 包com.yang.base.resoucebundle 下的 myres.properties  myres_zh_CN.properties
	 * @return 
	 * ResourceBundleUtil
	 * @throws
	 */
	public static ResourceBundleUtil getInstance(String baseName){ 
		ResourceBundleUtil resourceBundleUtil =new ResourceBundleUtil(baseName);
		return resourceBundleUtil;
	}
	
	/**
	 * <p>Title: </p>
	 * <p>Description: 初始化 </p>
	 * @param baseName
	 */
	private ResourceBundleUtil(String baseName){
		resourceBundle =ResourceBundle.getBundle("com.yang.base.resoucebundle.myres", getLocaleDefault()); 
	}
	
	public String getString(String key){
		return  resourceBundle.getString(key); 
	}
	
	 
	public Integer getInteger(String key){
		String temp  =	resourceBundle.getString(key);  
		return Integer.parseInt(temp);
	}
	
 
	public Float getFLoat(String key){
		String temp  =	resourceBundle.getString(key);   
		return  	Float.parseFloat(temp);
	}

	public Double getDouble(String key){
		String temp  =	resourceBundle.getString(key);   
		return Double.parseDouble(temp); 
	}
	 
	
	/**
	 * @Description: 打印此文件.
	 * @return
	 * String
	 * @throws
	 */
	public void println(){
		 Set<String>  keySet=resourceBundle.keySet();
		 for (String keyItem : keySet) {
			System.out.println("key:"+keyItem+ "  value:"+resourceBundle.getString(keyItem));
		} 
	}
	
	public static  Locale getLocaleDefault(){
		return Locale.getDefault(); 
	}
	
	public static void main(String[] args) {

        Locale locale1 = new Locale("zh", "CN"); 
        ResourceBundle resb1 = ResourceBundle.getBundle("com.yang.base.resoucebundle.myres", locale1); 
        
	     String[] tts =   resb1.getStringArray("bbb");
	     
	     for (String string : tts) {
	    	 System.out.println(string);
		}
     
        System.out.println(resb1.getString("aaa")); 
        

        ResourceBundle resb2 = ResourceBundle.getBundle("com.yang.base.resoucebundle.myres", Locale.getDefault()); 
        System.out.println(resb2.getString("aaa")); 

        Locale locale3 = new Locale("en", "US"); 
        ResourceBundle resb3 = ResourceBundle.getBundle("com.yang.base.resoucebundle.myres", locale3); 
        System.out.println(resb3.getString("aaa")); 
	}

}
