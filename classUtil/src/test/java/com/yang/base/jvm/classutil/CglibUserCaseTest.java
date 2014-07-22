package com.yang.base.jvm.classutil;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.common.RayTest;

/**
 * @Description	: http://ray-yui.iteye.com/blog/2026426
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午3:51:35 
 */
public class CglibUserCaseTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}
	

	  
    // 第一种写法,使用Enhancer静态create方法  
    /**
     * @Description: 
     * 这种方法,需要无参构造函数 (必需是对旬象不能是接口)
  	通过上面的代码可以看出.真正处理代理业务逻辑也就是JDK动态代理中InvocationHandler中invoke方法的是使用enhancer.setCallback传递Callback接口实现来进行.
  	上面例子中我们使用MethodInterceptor,MethodInterceptor能满足所有拦截的业务需求,
  	但一些已经普遍存在的动态代理使用方式,例如使用动态代理实现延迟加载,CGLIB为我们提供了更方便和简化的实现 
     * void
     * @throws
     */
    @Test  
    public void testCGLIBProxy01() { 
        /* 
         * create有多种重载方式,此重载第一个参数为超类(本类)的类型, 
         * 若然需要动态代理的类没有实现接口,就需要填写第一个参数, 
         * 第二个参数为Class类型的数组,当动态代理的类有实现接口, 
         * 可以选择性填写第二个参数,否则为null 
         */
        RayTest test = (RayTest) Enhancer.create(RayTest.class, null,  
                new MethodInterceptor() { 
                    // 真实主题  
                    RayTest test = new RayTest();  
  
                    @Override  
                    public Object intercept(Object arg0, Method method,  
                            Object[] args, MethodProxy arg3) throws Throwable {  
  
                        // 动态代理增加的logic  
                        String before = "before ";  
                        // 使用MethodProxy调用效率更高  
                        Object str = arg3.invoke(test, args);  
                        String after = " after";  
                        return before + str + after;  
                    }
                });  
        		System.out.println(test.execute()); 
    }  
  
    // 第二种写法,创建Enhancer对象  
    /**
     * @Description: TODO
     * void
     * @throws
     */
    @Test  
    public void testCGLIBProxy2() {  
        // 创建Enhancer类  
        Enhancer enhancer = new Enhancer();  
        // 当类没有实现接口而又需要动态代理,使用setSuperclass  
        enhancer.setSuperclass(RayTest.class);  
        // 当类实现了接口需要动态代理,使用setInterface  
        enhancer.setInterfaces(new Class[] {});  
        enhancer.setCallback(new MethodInterceptor() {  
            // 真实主题  
            RayTest test = new RayTest();  
  
            @Override  
            public Object intercept(Object arg0, Method method, Object[] args,  
                    MethodProxy arg3) throws Throwable {  
  
                // 动态代理增加的logic  
                String before = "before ";  
                // 使用MethodProxy调用效率更  
                Object str = arg3.invoke(test, args);  
                String after = " after";  
                return before + str + after;  
            }  
        });  
        RayTest rayTestProsy = (RayTest) enhancer.create();
        	System.out.println(rayTestProsy.execute()); 
  
    }  
    

    /**
     * @Description: 
		但一些已经普遍存在的动态代理使用方式,例如使用动态代理实现延迟加载,CGLIB为我们提供了更方便和简化的实现 
     * void
     * @throws
     */
    @Test  
    public void testCGLIBUseSuperClass() {
    	Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(RayTest.class);   
        // NoOp实现类会使用默认的父类实现,没增加任何logic  
        enhancer.setCallback(NoOp.INSTANCE);  
        RayTest test = (RayTest) enhancer.create();  
        Assert.assertNotNull(test);  
    }  
  
    /**
     * @Description: 
但一些已经普遍存在的动态代理使用方式,例如使用动态代理实现延迟加载,CGLIB为我们提供了更方便和简化的实现 
     * void
     * @throws
     */
    @Test  
    public void testCGLIBLazyObject() {  
    	Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(RayTest.class);  
        //实现LazyLoader接口,在创建对象时可以增加业务logic和创建对象的子类等  
        enhancer.setCallback(new LazyLoader() { 
            @Override  
            public Object loadObject() throws Exception {  
                final RayTest lazyTest = new RayTest();  
                return lazyTest;  
            }  
        });  
        RayTest test = (RayTest) enhancer.create();  
        Assert.assertNotNull(test);  
    }  
    
    
    /* 
     * 我們假设RayTest类不存在默认构造函数, 
     * 只提供需要传入 String和Integer作为参数的构造函数 
     */  
    @Test  
    public void testNotExistDefaultConstructor() {  

    	Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(RayTest.class);
        
       // enhancer.setCallback(NoOp.INSTANCE);  
        // 可以使用create的重载方式,参数1是构造函数参数类型,参数2是值  
      //  RayTest test = (RayTest) enhancer.create(new Class[] { String.class,  
       //         Integer.class }, new Object[] { "Hello World", 100 });  
       // Assert.assertNotNull(test);  
        
    }  
    
    
    @Test  
    public void testCGLIBCallbackFilter() {  
    	Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(RayTest.class);
    	
        // 创建callback1  
        Callback callback1 = new MethodInterceptor() {  
            // 真是主题类  
            RayTest test = new RayTest();  
  
            @Override  
            public Object intercept(Object obj, Method method, Object[] args,  
                    MethodProxy proxy) throws Throwable {  
                String before = "callback1 before ";  
                Object str = proxy.invoke(test, args);  
                String after = " callback1 after";  
                return before + str + after;  
            }  
        };  
        // 创建callback2  
        Callback callback2 = new MethodInterceptor() {  
  
            // 真是主题类  
            RayTest test = new RayTest();  
  
            @Override  
            public Object intercept(Object obj, Method method, Object[] args,  
                    MethodProxy proxy) throws Throwable {  
                String before = "callback2 before ";  
                Object str = proxy.invoke(test, args);  
                String after = " callback2 after";  
                return before + str + after;  
            }  
        };  
        // 使用setCallbacks设置多个Callback  
        enhancer.setCallbacks(new Callback[] { callback1, callback2 });  
   
        enhancer.setCallbackFilter(new CallbackFilter() {  
            static final int EXECUTE_METHOD = 0;  
            static final int OTHER_METHOD = 1;
            /* 
             * accept需要返回一個int类型, 
             * 该int类型为上文中setCallbacks设置的多个 
             * Callback处理逻辑的数组的下标,上文中设置了两个Callback, 
             * 分别为callback1和callback2 
             */  
  
            @Override  
            public int accept(Method method) {  
                /* 
                 * Method参数代表代理类的执行方法,  
                 * 以下logic为 判断执行方法名称是否为execute, 
                 * 是则执行callback1,也就是数组下标为0的逻辑,  
                 * 否则执行Other逻辑 
                 */  
                if ("execute".equals(method.getName()))  
                    return EXECUTE_METHOD;  
                else  
                    return OTHER_METHOD;  
            }  
        });  
        RayTest test = (RayTest) enhancer.create();  
        
        String executeResult = test.execute();  
       // Assert.assertEquals("callback1 before execute callback1 after",     executeResult);  
        String otherResult = test.action();  
        //Assert.assertEquals("callback2 before action callback2 after",  otherResult);  
    }  

}
