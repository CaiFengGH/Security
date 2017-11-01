package com.caifeng.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.caifeng.validator.SimpleConstraint;
import com.fasterxml.jackson.annotation.JsonView;

public class User {
	
	public interface UserSimpleView {};
	public interface UserDetailView extends UserSimpleView{};
	
	@SimpleConstraint(message = "用户名测试")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	
	private String id;
	//日期类型储存为统一格式的时间戳
	@Past(message = "生日为过去时间")
	private Date birthday;
	
	@JsonView(UserSimpleView.class)
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	
	@JsonView(UserDetailView.class)
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
