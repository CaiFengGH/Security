package com.caifeng.async;

import java.util.concurrent.Callable;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;

@RestController
public class AsyncController {

	private Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	 
	/**
	 * @author Ethan
	 * @desc 基于Runnable的异步处理 
	 */
	@RequestMapping("/order1")
	public Callable<String> order1(){
		logger.info("主线程开始");
		Callable<String> call = new Callable<String>(){

			@Override
			public String call() throws Exception {
				 logger.info("副线程开始");
				 Thread.sleep(1000);
				 logger.info("副线程结束");
				 return "order success";
			}
		};
		logger.info("主线程结束");
		return call;
	}
}
