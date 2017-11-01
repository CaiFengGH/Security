package com.caifeng.security.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.caifeng.security.social.qq.api.QQ;
import com.caifeng.security.social.qq.api.QQImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{
	
	//将用户导向认证服务器的地址
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
	//用户获取授权码后，向服务提供商获取token的地址
	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

	private String appId;
	
	//前面五个步骤
	public QQServiceProvider(String appId,String appSecret) {
		//父类调用OAuth2Operation，生成默认的实现类
		//appId和appSecret为应用在注册时获取的标示，由系统配置获取
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId; 
	}
	
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken,appId);
	}
}
