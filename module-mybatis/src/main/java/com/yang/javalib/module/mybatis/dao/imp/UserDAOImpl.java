package com.yang.javalib.module.mybatis.dao.imp;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.yang.javalib.module.mybatis.dao.UserDAO;
import com.yang.javalib.module.mybatis.pojo.User;

/**
 * @Description: 这一种需要自己 调用 . . 我们可以用面向接口的方式 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-2 下午9:50:47 
 */
public class UserDAOImpl  extends SqlSessionDaoSupport implements UserDAO {

	@Override
	public User queryUserById(int id) {
		return (User)this.getSqlSession().selectOne("com.yang.javalib.module.mybatis.dao.imp.UserDAOImpl.queryUserByID", id);  
	}

}
