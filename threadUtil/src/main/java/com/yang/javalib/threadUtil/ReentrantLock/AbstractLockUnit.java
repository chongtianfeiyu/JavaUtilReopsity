package com.yang.javalib.threadUtil.ReentrantLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * @Description	: 抽象锁单元 unit,很完美呀..
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-4-18 上午8:32:41 
 * @param <Key>
 * @param <Value>
 */
public abstract class AbstractLockUnit<Key,Value>  implements ILockUnit<Key,Value> { 
	private static final Logger logger =Logger.getLogger(AbstractLockUnit.class);
	
	private  Map<Key,Value> lockRepo=new ConcurrentHashMap<Key,Value>();

	
	public AbstractLockUnit() {
		super();
		init();
	}

	/**
	 * @Description: 初始始化该锁库,放入一些信息来
	 * @return
	 */
	public boolean init(){
		if (logger.isDebugEnabled()) {
			logger.debug("init() - start"); //$NON-NLS-1$
		}
		 
		lockRepo.putAll(fullLockUnit());
		
		if (logger.isDebugEnabled()) {
			logger.debug("init() - end"); //$NON-NLS-1$
		}
		return true ;
	}
	
	/**
	 * @Description: 填充锁库,这个方法由lockRepository调用
	 * @return
	 * boolean
	 * @throws
	 */
	public abstract Map<Key,Value> fullLockUnit();
	
	/**
	 * @Description: 通过key得到一个锁对象　．
	 * @param key
	 * @return
	 */
	public Value getOne(Key key){
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(T) - start"); //$NON-NLS-1$
		}

		Value returnReentrantLock = lockRepo.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(T) - end"); //$NON-NLS-1$
		}
		return  returnReentrantLock; 
	}
	/**
	 * @Description: 添加一个锁对象．　
	 * @param key
	 * @return
	 */
	public boolean putOne(Key key,Value value){
		if (logger.isDebugEnabled()) {
			logger.debug("putOne(T) - start"); //$NON-NLS-1$
		}

		lockRepo.put(key, value);

		if (logger.isDebugEnabled()) {
			logger.debug("putOne(T) - end"); //$NON-NLS-1$
		}
		return true ;
	}
}
