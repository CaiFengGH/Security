package com.caifeng.security.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.caifeng.security.properties.QQProperties;
import com.caifeng.security.properties.SecurityProperties;
import com.caifeng.security.social.qq.connect.QQConnectFactory;

//当配置了这个字段时，才生效
@Configuration
@ConditionalOnProperty(prefix = "caifeng.security.social.qq", name="app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqProperties = securityProperties.getSocial().getQQProperties();
		
		return new QQConnectFactory(qqProperties.getProviderId(),qqProperties.getAppId(),
				qqProperties.getAppSecret());
	}
}
