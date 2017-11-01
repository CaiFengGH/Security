package com.caifeng.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.caifeng.exception.UserNotFoundException;


/**
 * @author Ethan
 * @desc 接收控制器传递过来的异常，对控制器异常进行增强
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,Object> handlerUserNotFoundException(UserNotFoundException e){
		Map<String,Object> res = new HashMap<String,Object>(); 
		res.put("id", e.getId());
		res.put("message", e.getMessage());
		return res;
	}
}
