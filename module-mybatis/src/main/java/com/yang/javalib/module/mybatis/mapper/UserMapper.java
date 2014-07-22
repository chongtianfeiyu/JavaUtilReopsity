package com.yang.javalib.module.mybatis.mapper;

import java.util.List;

import com.yang.javalib.module.mybatis.pojo.User;

/**  
 * 对用户操作的接口  
 * 在同一包名下，创建与接口对应的mapper： 以接口的方式,进行调用 .
 * 接口名与XML 文件名一致.
 * 另外文件需要配置 . 
 * @author malei  
 *  
 */  
public interface UserMapper {  
    /**  
     * 取得所有用户  
     * @return List<User>  
     */  
    public List<User> getAll();  
    /**  
     * 根据id取得用户  
     * @param id  
     * @return User  
     */  
    public User get(int id);  
    /**  
     * 添加用户  
     * @param user  
     */  
    public void addUser(User user);  
    /**  
     * 修改用户信息  
     * @param user  
     */  
    public void modifyUser(User user);  
    /**  
     * 根据id删除用户  
     * @param id  
     */  
    public void deleteUser(int id);  
}  