package com.caifeng.code;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.caifeng.security.validate.code.ImageCode;
import com.caifeng.security.validate.code.ValidateCodeGenerator;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

	@Override
	public ImageCode generate(HttpServletRequest request) {
		System.out.println("这是应用中的code生成器");
		return null;
	}
}
