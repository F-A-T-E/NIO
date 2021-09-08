package com.netty.web.nettyrpc.pojo;

import java.util.Date;

public class User {

	private int age;
	private Date birthday;
	private String name;

	public User(){

	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
