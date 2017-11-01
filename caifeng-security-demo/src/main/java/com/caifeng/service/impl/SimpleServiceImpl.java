package com.caifeng.service.impl;

import org.springframework.stereotype.Service;
import com.caifeng.service.SimpleService;

@Service
public class SimpleServiceImpl implements SimpleService {

	public String greet(String message) {
		System.out.println("greet:");
		return "hello"+message;
	}
}
