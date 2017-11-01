package com.caifeng.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//@Component
public class TimeInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception e)
			throws Exception {
		System.out.println("afterCompletion");
		Long startTime = (Long) request.getAttribute("start"); 
		Long endTime = new Date().getTime();
		System.out.println("end time:"+(endTime - startTime));
//		System.out.println("exception:"+e.getMessage());
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("postHandler");
		Long startTime = (Long) request.getAttribute("start"); 
		Long processTime = new Date().getTime();
		System.out.println("process time:"+(processTime - startTime));
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("preHandler");
		//handler的基本信息
		System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
		System.out.println(((HandlerMethod)handler).getMethod().getName());
		
		request.setAttribute("start", new Date().getTime());
		return true;
	}
}
