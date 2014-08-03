package com.yang.base.jvm.classutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * http://paddy-w.iteye.com/blog/841798
 *
 */
public class InvocationHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
        Subject rs=new RealSubject();//这里指定被代理类
        InvocationHandler ds=new DynamicSubject(rs);
        Class<?> cls=rs.getClass();

        //以下是一次性生成代理

        Subject subject=(Subject) Proxy.newProxyInstance(
                cls.getClassLoader(), cls.getInterfaces(), ds);

        //这里可以通过运行结果证明subject是Proxy的一个实例，这个实例实现了Subject接口
        System.out.println(subject instanceof Proxy);

        //这里可以看出subject的Class类是$Proxy0,这个$Proxy0类继承了Proxy，实现了Subject接口
        System.out.println("subject的Class类是："+subject.getClass().toString());

        System.out.print("subject中的属性有：");

        Field[] field=subject.getClass().getDeclaredFields();
        for(Field f:field){
            System.out.print(f.getName()+", ");
        }

        System.out.print("\n"+"subject中的方法有：");

        Method[] method=subject.getClass().getDeclaredMethods();

        for(Method m:method){
            System.out.print(m.getName()+", ");
        }

        System.out.println("\n"+"subject的父类是："+subject.getClass().getSuperclass());

        System.out.print("\n"+"subject实现的接口是：");

        Class<?>[] interfaces=subject.getClass().getInterfaces();

        for(Class<?> i:interfaces){
            System.out.print(i.getName()+", ");
        }

        System.out.println("\n\n"+"运行结果为：");
        subject.request();
    }
}


/**
 * 抽象角色（动态代理只能代理接口）
 */
interface Subject {
    public void request();
}

/**
 * 真实角色：实现了Subject的request()方法
 */
class RealSubject implements Subject{

    public void request(){
        System.out.println("From real subject.");
    }
}

/**
 * 实现了InvocationHandler
 */
class DynamicSubject implements InvocationHandler
{
    private Object obj;//这是动态代理的好处，被封装的对象是Object类型，接受任意类型的对象

    public DynamicSubject()
    {
    }

    public DynamicSubject(Object obj)
    {
        this.obj = obj;
    }

    //这个方法不是我们显示的去调用
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        System.out.println("before calling " + method);

        method.invoke(obj, args);

        System.out.println("after calling " + method);

        return null;
    }

}

