package com.yang.javalib.threadUtil.ReentrantLock;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-4-18 上午8:20:30 
 */
public class LockUnitObject extends   AbstractLockUnit<Integer, ReentrantLock>  {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(LockUnitObject.class);

	
	@Override
	public Map<Integer, ReentrantLock> fullLockUnit() {
		if (logger.isDebugEnabled()) {
			logger.debug("fullLockUnit() - start"); //$NON-NLS-1$
		}
		
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("fullLockUnit() - end"); //$NON-NLS-1$
		}
		return null;
	}}
