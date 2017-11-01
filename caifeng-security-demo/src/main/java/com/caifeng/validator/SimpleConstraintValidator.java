package com.caifeng.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.caifeng.service.impl.SimpleServiceImpl;

//自动转为bean
public class SimpleConstraintValidator implements ConstraintValidator<SimpleConstraint, Object> {

	@Autowired
	private SimpleServiceImpl service;
	
	public void initialize(SimpleConstraint arg0) {
		System.out.println("simple validator init");
	}

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		service.greet("world");
		System.out.println(value);
		return false;
	}

}
