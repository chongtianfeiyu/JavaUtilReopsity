package com.yang.javalib.threadUtil.ReentrantLock;

import java.util.Map; 

public interface ILockUnit  <Key,Value> {

	
	public  Map<Key,Value> fullLockUnit();
	 
	public  Value getOne(Key key);  
	
	public boolean putOne(Key key,Value value);
	 
	
	
	
}
