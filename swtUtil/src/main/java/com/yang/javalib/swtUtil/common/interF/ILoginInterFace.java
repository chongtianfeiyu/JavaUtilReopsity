package com.yang.javalib.swtUtil.common.interF;

/**
 * @Description: 登录验证接口
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-24 下午6:03:26 
 */
public interface ILoginInterFace {

	/**
	 * @Description: 验证登录 是否成功
	 * @param userName
	 * @param passWord
	 * @return
	 */
	Boolean loginCheck(String userName, String passWord);

	/**
	 * @Description: 登录成功后
	 */
	void loginSucc();

	/**
	 * @Description:  登录失败后
	 */
	void loginFail();
}
