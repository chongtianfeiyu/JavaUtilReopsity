package com.yang.javalib.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.yang.javalib.common.MyAnnotation.MyClassAnnotation;
import com.yang.javalib.common.MyAnnotation.MyConstructorAnnotation;
import com.yang.javalib.common.MyAnnotation.MyFieldAnnotation;
import com.yang.javalib.common.MyAnnotation.MyMethodAnnotation;
import com.yang.javalib.common.MyAnnotation.Yts;
import com.yang.javalib.common.MyAnnotation.Yts.YtsType;



@MyClassAnnotation(desc = "The class", uri = "com.test.annotation.Test")
@Yts(classType =YtsType.util)
public class TestAnnotation {
    @MyFieldAnnotation(desc = "The class field", uri = "com.test.annotation.Test#id")
    private String id;

    @MyConstructorAnnotation(desc = "The class constructor", uri = "com.test.annotation.Test#MySample")
    public TestAnnotation() {
    }

    public String getId() {
        return id;
    }

    @MyMethodAnnotation(desc = "The class method", uri = "com.test.annotation.Test#setId")
    public void setId(String id) {
        System.out.println(" method info: "+id);
        this.id = id;
    }
    
    @MyMethodAnnotation(desc = "The class method sayHello", uri = "com.test.annotation.Test#sayHello")
    @Yts  
    public void sayHello(String name){  
        if(name == null || name.equals("")){  
            System.out.println("hello world!");  
        }else{  
            System.out.println(name + "\t:say hello world!");  
        }  
    }  
    public static void main(String[] args) throws Exception {

        Class<TestAnnotation> clazz = TestAnnotation.class;
        // 得到类注解
        MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyClassAnnotation.class);
        System.out.println(myClassAnnotation.desc() + " "+ myClassAnnotation.uri());

        // 得到构造方法注解
        Constructor<TestAnnotation> cons = clazz.getConstructor(new Class[]{});
        MyConstructorAnnotation myConstructorAnnotation = cons.getAnnotation(MyConstructorAnnotation.class);
        System.out.println(myConstructorAnnotation.desc() + " "+ myConstructorAnnotation.uri());

        // 获取方法注解
        Method method = clazz.getMethod("setId", new Class[]{int.class});
        MyMethodAnnotation myMethodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
        System.out.println(myMethodAnnotation.desc() + " "+ myMethodAnnotation.uri());
        // 获取字段注解
        Field field = clazz.getDeclaredField("id");
        MyFieldAnnotation myFieldAnnotation = field.getAnnotation(MyFieldAnnotation.class);
        System.out.println(myFieldAnnotation.desc() + " "+ myFieldAnnotation.uri());
    }

}