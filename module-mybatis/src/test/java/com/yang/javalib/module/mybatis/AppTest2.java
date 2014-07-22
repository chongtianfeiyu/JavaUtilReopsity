package com.yang.javalib.module.mybatis;

import static org.junit.Assert.*;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: 第二种启动方式 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-2 下午9:24:57 
 */
public class AppTest2 {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader    = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
    
    public static void main(String[] args) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
        //User user = (User) session.selectOne("com.yihaomen.mybatis.models.UserMapper.selectUserByID", 1);
        //System.out.println(user.getUserAddress());
        //System.out.println(user.getUserName());
        } finally {
        session.close();
        }
    }}
