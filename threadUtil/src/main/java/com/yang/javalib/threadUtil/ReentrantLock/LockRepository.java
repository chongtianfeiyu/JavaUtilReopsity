package com.yang.javalib.threadUtil.ReentrantLock;

/**
 * @Description	: 锁库,包含各种锁呀. 
 * 啥锁都有. 
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-4-17 上午7:56:15 
 */
public class LockRepository {
	
	/**
	 * @Fields orderInfoLockUnit : 订单锁库
	 */
	LockUnitReentrant orderInfoLockUnit ;
	
	/**
	 * @Fields goodInfoSellUnit : 商品锁库
	 */
	LockUnitReentrant  goodInfoSellUnit ;

}
