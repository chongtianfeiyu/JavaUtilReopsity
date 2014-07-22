package com.yang.javalib.common;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Description	: 自定义的注解类
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-3-21 下午12:54:29 
 */
public class MyAnnotation {

    
    /**
     * 注解类
     * @author T4980D
     *
     */
    @Retention(RetentionPolicy.RUNTIME)  
    @Target(ElementType.TYPE)  
    public @interface MyClassAnnotation {  
        String uri();  
        String desc();  
    }  
    
    /** 
     * 构造方法注解 
     * @author T4980D 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.CONSTRUCTOR)   
    public @interface MyConstructorAnnotation {  
      
        String uri();  
        String desc();  
    }  
    
    /** 
     * 我的方法注解 
     * @author Owner 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.METHOD)  
    public @interface MyMethodAnnotation {  
      
        String uri();  
        String desc();  
    }  
    
    /** 
     * 字段注解定义 
     * @author Owner 
     * 
     */  
    @Retention(RetentionPolicy.RUNTIME)     
    @Target(ElementType.FIELD)   
    public @interface MyFieldAnnotation {  
      
        String uri();  
        String desc();  
    }  
    /**
     * 
     * 可以同时应用到类上和方法上
     * @author T4980D
     *
     */
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Yts {
        // 定义枚举
        public enum YtsType {
            util, entity, service, model
        }

        // 设置默认值
        public YtsType classType() default YtsType.util;
        
        // 数组
        int[] arr() default {3, 7, 5};

        String color() default "blue";
    }
       

}
