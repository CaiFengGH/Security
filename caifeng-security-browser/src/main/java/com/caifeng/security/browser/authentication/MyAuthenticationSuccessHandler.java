package com.caifeng.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.caifeng.security.properties.LoginType;
import com.caifeng.security.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityProperties securityProperties;
	
	//springBoot开启时自动登录
	@Autowired
	private ObjectMapper objectMap;
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("登录成功，开始授权");
		//如果为json格式，则返回json数据，如果不是则跳转
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
			response.setContentType("application/json;charset=UTF-8");
			//将authentication以json的形式输出到前端
			response.getWriter().write(objectMap.writeValueAsString(authentication));
		}else{
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
