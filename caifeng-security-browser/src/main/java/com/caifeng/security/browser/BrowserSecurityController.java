package com.caifeng.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.caifeng.security.browser.support.SimpleResponse;
import com.caifeng.security.properties.SecurityProperties;

@RestController
public class BrowserSecurityController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse require(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		//获取保存的请求
		SavedRequest saveRequest = requestCache.getRequest(request, response);
		//从保存请求中获取跳转的url
		String targetUrl = saveRequest.getRedirectUrl();
		//判断是否是Html请求
		if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
			logger.info("跳转的url:"+targetUrl);
			//标准登陆页面和自定义登录页面
			redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLogin());
		}
		
		return new SimpleResponse("访问的页面需要用户登录，引导用户到登录页面");
	}
}
