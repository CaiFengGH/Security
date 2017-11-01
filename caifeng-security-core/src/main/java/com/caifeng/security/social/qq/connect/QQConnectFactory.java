package com.caifeng.security.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import com.caifeng.security.social.qq.api.QQ;

/**
 * @author Ethan
 * @desc connection工厂
 */
public class QQConnectFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectFactory(String providerId,
			String appId, String appSecret) {
		
		//将提供商和适配器提供进去
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}
}
