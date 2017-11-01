package com.caifeng.security.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ethan
 * @desc 多实例的组件，不能声明为component
 */
public class QQOAuth2Template extends OAuth2Template {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QQOAuth2Template(String clientId, String clientSecret,
			String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//带上需要的参数
		setUseParametersForClientAuthentication(true);
	}

	//由于QQ字段的特殊情况
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl,
			MultiValueMap<String, String> parameters) {
		
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		
		logger.info("获取响应为:"+responseStr);
		
		//根据&符号将其进行拆分
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
		//请求的标准按照QQ的格式做解析
		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[2], "=");
		
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	
	//用户重定向后并未进入登录页面
	@Override
	protected RestTemplate createRestTemplate() {
		
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters()
		//增加新的converter转换器使其可以处理text/html
					.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		return restTemplate;
	}
	
}
