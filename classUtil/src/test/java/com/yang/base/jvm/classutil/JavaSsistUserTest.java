package com.yang.base.jvm.classutil;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.common.RayTest;

public class JavaSsistUserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}


	  /**
	 * @Description: 生成代理 用例
	 * http://ray-yui.iteye.com/blog/2029261
	 * @throws Exception
	 * void
	 * @throws
	 */
	public static void testJavassistFactoryProxy() throws Exception {  
	        // 创建代理工厂  
	        ProxyFactory proxyFactory = new ProxyFactory();  
	  
	        // 设置被代理类已实现接口(可选)  
	        // proxyFactory.setInterfaces(new Class[] {});  
	  
	        // 设置被代理类的类型  
	        proxyFactory.setSuperclass(RayTest.class);  
	  
	        // 创建代理类型的Class  
	        Class<ProxyObject> proxyClass = proxyFactory.createClass();  
	  
	        // 注意若然父类没有无参构造函数,需要使用createClass方法的重載  
	        // RayTest proxyTest = (RayTest) proxyFactory.create(new Class[] {  
	        // Integer.class }, new Object[] { 0 });  
	  
	        RayTest proxyTest = (RayTest) proxyClass.newInstance();  
	  
	        ((ProxyObject) proxyTest).setHandler(new MethodHandler() {   
	            // 真实主題  
	            RayTest test = new RayTest();   
	            
	            @Override  
	            public Object invoke(Object self, Method thisMethod,  
	                    Method proceed, Object[] args) throws Throwable {  
	                String before = "before ";  
	                Object str = thisMethod.invoke(test, args);  
	                String after = " after";  
	                return before + str + after;  
	            }

	        });  
	  
	        Assert.assertEquals("before execute after", proxyTest.execute());  
	    }  
	

	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NotFoundException 
	 * @throws CannotCompileException 
	 * @Description: 动态创建类用例
	 * void
	 * @throws
	 */
	public static void dynamicCreateClass() throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException{ 
		 // 创建类池,true为使用默认路径  
        ClassPool classPool = new ClassPool(true);
        String className = RayTest.class.getName();  
        CtClass ctClass = classPool.makeClass(className + "JavassitProxy");
  
        // 添加接口,可选  
        // ctClass.addInterface(classPool.get(RayTestInterface.class.getName()));  
  
        // 添加超类  
        ctClass.setSuperclass(classPool.get(RayTest.class.getName()));  
  
        // 添加默认构造函数  
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));  
  
        // 添加屬性  
        ctClass.addField(CtField.make("public " + className + " real = new " + className + "();", ctClass));  //真正的类. 
  
        // 添加方法,里面进行动态代理logic  
        ctClass.addMethod(CtNewMethod.make("public String execute(){ return \"before \" + real.execute() + \" after\";}", ctClass));  
  
        Class<RayTest> testClass = ctClass.toClass(); 
        RayTest test = testClass.newInstance();  
        System.out.println( test.execute());
       
	}
}

