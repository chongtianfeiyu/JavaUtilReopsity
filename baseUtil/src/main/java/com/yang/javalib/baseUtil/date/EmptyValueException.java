package com.yang.javalib.baseUtil.date;

/**
 * 
 * @author Jinson Chen
 *
 */

public class EmptyValueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmptyValueException() {
		super();
	}
	
	public EmptyValueException(String msg) {
		super(msg);
	}
	
	public EmptyValueException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public EmptyValueException(Throwable cause) {
		super(cause);
	}
}
