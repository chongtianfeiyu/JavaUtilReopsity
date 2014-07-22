package com.yang.base.context;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Description	: 业务处理 上下文对象 .
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-4-28 上午9:14:51 
 */
public class BizContext {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(BizContext.class);

	private Map<String, Object> contextMap = new HashMap<String, Object>();

	public void putOne(String key, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("putOne(String, Object) - start"); //$NON-NLS-1$
		}

		contextMap.put(key, value);

		if (logger.isDebugEnabled()) {
			logger.debug("putOne(String, Object) - end"); //$NON-NLS-1$
		}
	}

	public Object getOne(String key) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(String) - start"); //$NON-NLS-1$
		}

		Object returnObject = contextMap.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(String) - end"); //$NON-NLS-1$
		}
		return returnObject;
	}

	public <M> M getOne(String key, M object) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(String, M) - start"); //$NON-NLS-1$
		}

		M returnM = (M) contextMap.get(key);
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(String, M) - end"); //$NON-NLS-1$
		}
		return returnM;
	}

	public <T> T getOne(String key, Class<T> clazz) {
		if (logger.isDebugEnabled()) {
			logger.debug("getOne(String, Class<T>) - start"); //$NON-NLS-1$
		}

		Object tt = contextMap.get(key);
		if (tt != null) {
			T returnT = (T) tt;
			if (logger.isDebugEnabled()) {
				logger.debug("getOne(String, Class<T>) - end"); //$NON-NLS-1$
			}
			return returnT;
		} else {
			throw new NoSuchElementException("没有找到相应对象，key:" + key);
		}
	}

}
