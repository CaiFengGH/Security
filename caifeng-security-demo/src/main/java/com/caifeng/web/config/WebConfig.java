package com.caifeng.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.caifeng.web.filter.TimeFilter;
import com.caifeng.web.interceptor.TimeInterceptor;

/**
 * @author Ethan
 * @desc 实现添加第三方过滤器
 */
//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注册interceptor方可使用
//		registry.addInterceptor(timeInterceptor);
	}

//	@Bean
	public FilterRegistrationBean timeFilter(){
		FilterRegistrationBean registBean = new FilterRegistrationBean();
		TimeFilter timeFilter = new TimeFilter();
		//注册过滤器
		registBean.setFilter(timeFilter);
		//配置过滤器作用的url
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		//注册url
		registBean.setUrlPatterns(urls);
		return registBean;
	}
}
