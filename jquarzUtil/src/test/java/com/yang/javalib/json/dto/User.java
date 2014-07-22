package com.yang.javalib.json.dto;

import java.io.Serializable;

public class User  implements Serializable {

	private int id;
	private String username;
	private int age;
	
	
	
	public User(int id, String username, int age) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
