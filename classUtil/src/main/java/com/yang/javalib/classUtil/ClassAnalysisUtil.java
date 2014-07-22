package com.yang.javalib.classUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;

/**
 * @Description	: 类解析 工具类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午1:02:12 
 */
public class ClassAnalysisUtil {

	/**
	 * @Description: 从object中得到类
	 * @param object
	 * @return
	 * Class<?>
	 * @throws
	 */
	public static Class<?> getClassFromObject(Object object) {

		return object.getClass();
	}

	/**
	 * @Description: 从类中得到全路径 名
	 * @param classInstance
	 * @return
	 * String
	 * @throws
	 */
	public static String getFullClassName(Class<?> classInstance) {

		// Class cls = java.util.Date.class;  
		// 加载字节码 封装成一个Class对象  
		String name = classInstance.getName();
		return name;
	}

	/**
	 * @Description: 从Class中得到类名
	 * @param classInstance
	 * @return
	 * String
	 * @throws
	 */
	public static String getSimpleClassName(Class<?> classInstance) {
		// Class cls = java.util.Date.class;  
		// 加载字节码 封装成一个Class对象  
		String name = classInstance.getSimpleName();
		return name;
	}

	/**
	 * @Description:  找出class隶属的package
	 * @param classInstance
	 * @return
	 * Package
	 * @throws
	 */
	public static Package getClassPackage(Class<?> classInstance) {

		Package p;
		p = classInstance.getPackage();

		if (p != null)
			return p;
		else {
			return null;
		}
		
	}
	


	
	public static void getClassTypeVariable(Class<?> classInstance) {
		TypeVariable<?>[] tv;  
		String x;
		 tv = classInstance.getTypeParameters(); //warning: unchecked conversion
		 for (int i = 0; i < tv.length; i++) {
		   x =  tv[i].getName(); //例如 E,K,V...
		   if (i == 0) //第一个
		       System.out.print("<" + x);
		   else //非第一个
		       System.out.print("," + x);
		   if (i == tv.length-1) //最后一个
		       System.out.println(">");
		 }
	}
	
	
	
	
	public static void getClassModifiers(Class<?> classInstance) {

		 int mod = classInstance.getModifiers();
		 System.out.print(Modifier.toString(mod)); //整个modifier
		 
		 if (Modifier.isInterface(mod))
			 System.out.print(" "); //关键词 "interface"已含于modifier
		 else
		   System.out.print(" class "); //关键词 "class"
		 
		  System.out.print(classInstance.getName()); //class名称
		 
	} 
	
	
	public static void getClassDeclaredFields(Class<?> classInstance) {

		Field[] ff = classInstance.getDeclaredFields();
		String x;
		
		for (int i = 0; i < ff.length; i++){
			x = ff[i].getType().getName();
			System.out.println("字段类型名:"+x);
		}

		System.out.println("=============================");
		
		Constructor<?>[] cn = classInstance.getDeclaredConstructors();
		for (int i = 0; i < cn.length; i++) {
			Class cx[] = cn[i].getParameterTypes(); 
			System.out.println("构造函数 :"+cn[i].getName());
			for (int j = 0; j < cx.length; j++){
				x = cx[j].getName(); 
				System.out.println("---------构造函数 参数类型 :"+x);
			}
		}
		

		System.out.println("=============================");
		Method[] mm = classInstance.getDeclaredMethods();
		
		for (int i = 0; i < mm.length; i++) { 
			System.out.println("函数名 :"+mm[i].getName());
			
			int md = mm[i].getModifiers();
			System.out.print("修饰符 :"+Modifier.toString(md));
			 
			Class cx[] = mm[i].getParameterTypes();
			for (int j = 0; j < cx.length; j++){
				x = cx[j].getName();
				System.out.println("---------函数参数类型:"+x); 
			}

			x = mm[i].getReturnType().getName();
			System.out.println("---------函数返回值类型:"+x);
		}
		//  classRef.remove(c.getName()); //不必记录自己（不需import自己）
	}
	

	public static void getClassSupperClasss(Class<?> classInstance) { 
	  Class supClass = classInstance.getSuperclass(); 
	 if (supClass != null) //如果有super class
	   System.out.print(" extends " +  supClass.getName());
	 
	}
	

	public static void getClassInterfaces(Class<?> classInstance) { 
		
		 Class cc[];
		 Class ctmp;
		 //找出所有被实现的interfaces
		 cc = classInstance.getInterfaces();
		 
		 if (cc.length != 0)
		   System.out.print(", /r/n" + " implements "); //关键词
		 
		 for (Class cite : cc) //JDK1.5新式循环写法
			 System.out.print(cite.getName());
	}
	
	public static void getInnerOuterClasses(Class<?> classInstance) { 
		
		 Class<?>[] cc = classInstance.getDeclaredClasses(); //找出inner classes 
		for (Class cite : cc)
		  System.out.println(cite.getName());

		 Class<?>  ctmp = classInstance.getDeclaringClass(); //找出outer classes
		if (ctmp != null)
		  System.out.println(ctmp.getName());
	}


}
