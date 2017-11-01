package com.caifeng.security.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ethan
 * @desc 服务提供商的接口实现
 * @note accessToken和restTemplate由父类提供
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

	//openId表示QQ用户的唯一标识
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
	//用来获取用户的信息：accessToken-openId-appId
	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	//第三方用户的唯一标识
	private String appId;

	private String openId;
	//将String类型转换为对象
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//accessToken由父类提供，appId由系统配置提供
	public QQImpl(String accessToken,String appId){
		//将accessToken作为参数封装起来
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
		//拼装获取openId的url
		String url = String.format(URL_GET_OPENID, accessToken);
		String result = getRestTemplate().getForObject(url, String.class);
		
		System.out.println(result);
		//从获取的内容中，节去处openId的内容
		this.openId = StringUtils.substringBetween(result, "\"openId\":\"","\"}");
	}
	
	public QQUserInfo getUserInfo() {
		
		String url = String.format(URL_GET_USERINFO, appId,openId);
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println(result);
		//将字符串转换为java对象
		QQUserInfo userInfo = null; 
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("将userInfo转为运行时异常");
		}
	}

}
