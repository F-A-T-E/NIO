package com.netty.web.nettyrpc.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private int age;
	private Date birthday;
	private String name;

	@Override
	public String toString() {
		return "User{" +
				"age=" + age +
				", birthday=" + birthday +
				", name='" + name + '\'' +
				'}';
	}

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
