package com.yang.javalib.classUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description	: 反射工具类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午2:11:41 
 */
/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午2:25:26 
 */
/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午2:25:28 
 */
/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午2:25:29 
 */
/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午2:25:32 
 */
public class ReflectUtil {
	
	public static Object getObjectFromClass(String classPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException{ 
		 Class c = Class.forName(classPath);
		 Object obj = null;
		 obj =c.newInstance(); //不带自变量 
		 return  obj ;   
	}

	public static Object getObjectFromClass(Class<?> classInstance) throws ClassNotFoundException, InstantiationException, IllegalAccessException{ 
		 return classInstance.newInstance();  
	}

	public static Object getObjectFromClass(String classPath,Object[] params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{ 
		 Class classInstance = Class.forName(classPath); 
		 return  getObjectFromClass(classInstance,params); 
	}

	
	public static Object getObjectFromClass(Class<?> classInstance,Object[] params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{ 

		 Class[] pTypes = getParasClass(params); 
 
	     Constructor ctor  = classInstance.getConstructor(pTypes);  
		 Object obj = null; 
		 obj =ctor.newInstance(params);
		 System.out.println(obj);  
		
		 return obj;  
	}
	
	/**
	 * @Description: 得到传入数组对应 的类数组
	 * @return
	 * Class[]
	 * @throws
	 */
	private static Class[] getParasClass(Object[] params){
		 
		 Class[] pTypes = new Class[params.length];

		 for (int i = 0; i < pTypes.length; i++) {
			 pTypes[i] = params[i].getClass(); 
		 }  
		 return  pTypes ;
	}
	
	/**
	 * @Description: 
		运行时调用methods
		这个动作和上述调用“带参数之ctor”相当类似。
		首先准备一个Class[]做为ctor的参数类型（本例指定其中一个是String，另一个是Hashtable），
		然后以此为自变量调用getMethod()，获得特定的Methodobject。
		接下来准备一个Object[]放置自变量，然后调用上述所得之特定Methodobject的invoke()，如图8。 
		知道为什么索取Methodobject时不需指定回返类型吗？因为method overloading机制要求signature（署名式）必须唯一，
		而回返类型并非signature的一个成份。换句话说，只要指定了method名称和参数列，就一定指出了一个独一无二的method。
	 * @param targetObject
	 * @param methodName
	 * @param params
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * void
	 * @throws
	 */
	public static void dynamicMethodInvoke(Object targetObject,String methodName,Object[] params) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{

		Class[] pTypes = new Class[params.length];
		Class classInstance = methodName.getClass();
		    
		Method m = classInstance.getMethod(methodName,pTypes); 
		Object result = m.invoke(targetObject, params); 
		System.out.println(result);
		
	}
	
	//运行时变更fields内容
	public static void dynamicChangeFiledValue(Object targetObject,String filedName,Object targetValue) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{

		Class c = targetObject.getClass();
		Field f = c.getField(filedName); //指定field 名称 
		System.out.println("字段 class类型为:"+f.getGenericType().getClass()); 
		System.out.println(filedName+" 原值: " +f.get(targetObject));
		 
		f.set(targetObject, targetValue);
		System.out.println(filedName+" 新值: " +targetValue);
		
	}
	
	/**
	 * @Description: TODO 动态调用setter方法, 会根据字段名自动组装方法.
	 * @param targetObject
	 * @param filedName
	 * @param targetValue 
	 * void
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	public static void dynamicSetterFiledValue(Object targetObject,String filedName,Object targetValue) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{

		
		
		Class ccls = targetObject.getClass();
		Method setMethod = ccls.getDeclaredMethod("set"+filedName,Integer.class);  
		 
		setMethod.invoke(targetObject, targetValue);//调用set方法  
		//f.set(targetObject, targetValue);
		System.out.println(filedName+" 新值: " +targetValue);
		
	} 

}
