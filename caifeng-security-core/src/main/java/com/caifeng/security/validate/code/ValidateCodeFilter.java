package com.caifeng.security.validate.code;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caifeng.security.properties.SecurityProperties;

public class ValidateCodeFilter 
				extends OncePerRequestFilter implements InitializingBean{

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	//自定义失败异常，MyAuthenticationFailureHandler
	private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
	
	private Set<String> urls = new HashSet<String>();
	
	private AntPathMatcher pathMatch = new AntPathMatcher();
	
	private SecurityProperties securityProperties;
	
	//在其他属性设置完毕后，添加自己的拦截url
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		String[] configUrls = StringUtils.
				split(securityProperties.getCode().getImage().getUrl(), ",");
		for(String configUrl : configUrls){
			urls.add(configUrl);
		}
		urls.add("/authentication/form");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		boolean flag = false;
		for(String url : urls){
			if(pathMatch.match(url, request.getRequestURI())){
				flag = true;
			}
		}
		
		//处理/authentication/form的请求
		if(flag){
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return ;
			}
		}
		//如果不是相应的请求，则直接调用相应的请求
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		ImageCode codeInSession = (ImageCode) sessionStrategy
				.getAttribute(request, ValidateCodeController.SESSION_KEY);
		//imageCode为login.html中的值
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		//判断验证码的逻辑
		if(StringUtils.isBlank(codeInRequest)){
			throw new ValidateCodeException("验证码不能为空");
		}
		if(codeInSession == null){
			throw new ValidateCodeException("验证码不存在");
		}
		if(codeInSession.isExpired()){
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
			throw new ValidateCodeException("验证码已经过期");
		}
		if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
	}
	
	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}
	
	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}
	
	public SimpleUrlAuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(
			SimpleUrlAuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
}
