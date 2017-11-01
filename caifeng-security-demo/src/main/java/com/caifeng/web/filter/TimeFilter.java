package com.caifeng.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

/**
 * @author Ethan
 * @desc 第三方过滤器框架添加
 */
//@Component
public class TimeFilter implements Filter {

	public void destroy() {
		System.out.println("Time filter destory");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("doFilter begin");
		Date date = new Date();
		Long begin = date.getTime();
		chain.doFilter(request, response);
		Long end = date.getTime();
		System.out.println("doFilter process time:"+(end - begin));
		System.out.println("doFilter end");
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Time filter initial");
	}
}
