package com.caifeng.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryCondition {
	
	public String username;
	@ApiModelProperty(value = "用户年龄起始")
	public int age;
	@ApiModelProperty(value = "用户年龄终止")
	public int ageTo;
	public String other;

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
	public int getAgeTo() {
		return ageTo;
	}
	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}
