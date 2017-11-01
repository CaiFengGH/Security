package com.caifeng.security.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.caifeng.security.properties.SecurityProperties;
import com.caifeng.security.validate.code.sms.DefaultSmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator(){
		ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
		imageCodeGenerator.setSecurityProperties(securityProperties);
		return imageCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = "smsCodeSender")
	public DefaultSmsCodeSender smsCodeSender(){
		return new DefaultSmsCodeSender();
	}
}
