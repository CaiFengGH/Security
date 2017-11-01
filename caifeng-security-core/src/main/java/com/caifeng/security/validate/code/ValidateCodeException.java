package com.caifeng.security.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Ethan
 * @desc 验证码异常处理
 */
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	public ValidateCodeException (String msg){
		super(msg);
	}
}
