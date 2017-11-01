package com.caifeng.security.social;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import com.caifeng.security.properties.SecurityProperties;

@Configuration
@EnableSocial//确保SpringSocial的相关属性可以起来
public class SocialConfig extends SocialConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		//自动寻找连接工厂-对数据库中的内容进行加解密
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository
										(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		//设置表前缀
		repository.setTablePrefix("caifeng_"); 
		return repository;
	}
	
	@Bean
	public SpringSocialConfigurer socialSecurityConfig(){
		//获取可配置的url
		String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
		MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
		//自动去配置实现url
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		return configurer;
	}
}
