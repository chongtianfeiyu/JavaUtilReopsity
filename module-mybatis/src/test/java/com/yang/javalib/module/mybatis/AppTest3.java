package com.yang.javalib.module.mybatis;

import static org.junit.Assert.*;
 
/**
 * @Description: 面向接口的调用 方式. 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-2 下午9:57:40 
 */


import java.io.IOException;  
import java.io.Reader;  
import org.apache.ibatis.io.Resources;  
import org.apache.ibatis.session.SqlSession;  
import org.apache.ibatis.session.SqlSessionFactory;  
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  
import org.junit.Test;  

import com.yang.javalib.module.mybatis.mapper.UserMapper;
   
   
public class AppTest3 {  
    private static SqlSession session;  
    private static UserMapper userMapper;  
    /**  
     * 类初始化的时候 先执行这段静态语句，读取mybatis.xml文件 完成接口和mapper的匹配  
     */  
    static{  
        //定义主配置文件的路径  
        String resource="mybatis.xml";  
        try {  
            Reader reader=Resources.getResourceAsReader(resource);  
            SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);  
            //打开一个会话  
            session=sessionFactory.openSession();  
            userMapper=session.getMapper(UserMapper.class);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    @Test  
    public void testGetAll(){  
        System.out.println(userMapper.getAll());  
    }  
      
    @Test  
    public void testGetById(){  
        System.out.println(userMapper.get(1));  
    }  
      
    @Test  
    public void testAddUser(){/*  
        User user=new User();  
        user.setUsername("zhangsan");  
        user.setPassword("123456");  
        userMapper.addUser(user);  
        //因为开启session的时候，是默认开启会话的，所以必须手动执行  
        session.commit();  
    */}  
      
    @Test  
    public void testModifyUser(){/*  
        User user=new User();  
        user.setId(1);  
        user.setUsername("root");  
        user.setPassword("123456");  
        userMapper.modifyUser(user);  
        session.commit();  
    */}  
      
    @Test  
    public void testDeleteUser(){  
        userMapper.deleteUser(4);  
        session.commit();  
    }  
}
