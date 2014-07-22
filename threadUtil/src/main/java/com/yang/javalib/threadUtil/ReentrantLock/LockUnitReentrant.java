package com.yang.javalib.threadUtil.ReentrantLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * @Description: ReentrantLock存储库
 * 可以把多个ReentrantLock放在这个库里面，如果需要则获取一个锁，
 * 然后进行锁定操作
 * 注意 :
 * ## 得到锁后一定要记得unlock,不然 你想想吧.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-13 下午10:11:01 
 */
public abstract class LockUnitReentrant extends AbstractLockUnit<Integer, ReentrantLock> {
	
	private static final Logger logger =Logger.getLogger(LockUnitReentrant.class);
	  
	public LockUnitReentrant() {
		super();
	}
	 
 
	
	/**
	 * @Description: 填充锁库,这个方法由lockRepository调用
	 * @return
	 * boolean
	 * @throws
	 */
	public  Map<Integer, ReentrantLock> fullLockUnit(){
		if (logger.isDebugEnabled()) {
			logger.debug("fullLockUnit() - start"); //$NON-NLS-1$
		} 
		return null ;
	}
	 

}
