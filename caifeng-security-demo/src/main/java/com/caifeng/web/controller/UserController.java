package com.caifeng.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caifeng.dto.User;
import com.caifeng.dto.UserQueryCondition;
import com.caifeng.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * @author Ethan
	 * @desc 查询用户信息列表
	 */
	@GetMapping()
	@JsonView(User.UserSimpleView.class)
	@ApiOperation(value = "查询用户信息列表")
	public List<User> query(UserQueryCondition condition,
			@PageableDefault(size=10,page=1,sort="username,asc") Pageable page){
	
		//将多个查询条件组装成一个对象
		System.out.println(ReflectionToStringBuilder.
				toString(condition,ToStringStyle.MULTI_LINE_STYLE));
		
		//将分页机制中的page相关信息打印出来
		System.out.println("size:"+page.getPageSize());
		System.out.println("num:"+page.getPageNumber());
		System.out.println("sort:"+page.getSort());

		List<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		return users;
	}
	
	/**
	 * @author Ethan
	 * @desc 获取用户信息
	 */
	@GetMapping("/{id:\\d+}")
	@JsonView(User.UserDetailView.class)
	public User getInfo(@ApiParam(value = "用户id") @PathVariable(name = "id") String userId){
//		throw new RuntimeException("被拦截器捕获");
//		throw new UserNotFoundException(userId);
		
		System.out.println("getInfo process");
		User user = new User();
		user.setusername("caifeng");
		return user;
	}
	
	/**
	 * @author Ethan
	 * @desc 增加用户
	 */
	@PostMapping
	@JsonView(User.UserSimpleView.class)
	public User create(@Valid @RequestBody User user,BindingResult errors){
		
		if(errors.hasErrors()){
			System.out.println("参数校验错误");
		}
		
		System.out.println(user.getId());
		System.out.println(user.getusername());
		System.out.println(user.getpassword());
		//后台传送时间戳，此处输出日期格式，测试部分输出时间戳
		System.out.println(user.getBirthday());
		
		user.setId("1");
		return user;
	}
	
	/**
	 * @author Ethan
	 * @desc 更新用户信息
	 */
	@PutMapping("/{id:\\d+}")
	@JsonView(User.UserSimpleView.class)
	public User update(@Valid @RequestBody User user,BindingResult errors){
		
		List<ObjectError> listError;
		if(errors.hasErrors()){
			//基于jdk8流式处理进行优化
			listError = errors.getAllErrors();
			Iterator<ObjectError> iterator = listError.iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next().getDefaultMessage());
			}
		}
		
		System.out.println(user.getId());
		System.out.println(user.getusername());
		System.out.println(user.getpassword());
		System.out.println(user.getBirthday());
		
		user.setId("1");
		return user;
	}
	
	/**
	 * @author Ethan
	 * @desc 删除用户信息
	 */
	@DeleteMapping("/{id:\\d+}")
	public void delete(@PathVariable(name = "id") String userId){
		System.out.println(userId);
	}
	
	/**
	 * @author Ethan
	 * @desc 注册用户
	 */
	@PostMapping("/register")
	public void register(User user){
		//注册用户
	}
}
