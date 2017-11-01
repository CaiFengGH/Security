package com.caifeng.web.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {
	
	//任何返回值 任何方法 任何参数
	//springFramework中Reference中第11章
	@Around("execution(* com.caifeng.web.controller.UserController.*(..))")
	public Object handlerControllerMethod(ProceedingJoinPoint jp) throws Throwable{
		System.out.println("time aspect begin");
		
		//获取请求的参数
		Object[] args = jp.getArgs();
		for(Object arg : args){
			System.out.println(arg);
		}
		
		Date date = new Date();
		Long begin = date.getTime();
		
		Object obj = jp.proceed();
		
		System.out.println("time adpect time:"+(date.getTime()-begin));
		System.out.println("time aspect end");
		
		return obj;
	}
}
