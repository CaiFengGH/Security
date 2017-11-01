package com.caifeng.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.caifeng.security.browser.support.SimpleResponse;
import com.caifeng.security.properties.LoginType;
import com.caifeng.security.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("myAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//springBoot开启时自动登录
	@Autowired
	private ObjectMapper objectMap;
	
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logger.info("登陆失败");
		
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			//将authentication以json的形式输出到前端
			response.getWriter().write(objectMap.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request,response,exception);
		}
	}
}
