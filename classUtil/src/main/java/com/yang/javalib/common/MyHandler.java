package com.yang.javalib.common;

import org.apache.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;

/**
 * @Description	: 代理处理类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午3:08:00 
 */
public class MyHandler implements InvocationHandler {
	private Object proxyObj;
	private static Logger log = Logger.getLogger(MyHandler.class);

	public Object bind(Object obj) {
		this.proxyObj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		try {
			//请在这里插入代码，在方法前调用
			log.info("调用log日志方法" + method.getName());
			result = method.invoke(proxyObj, args); //原方法
			//请在这里插入代码，方法后调用
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
