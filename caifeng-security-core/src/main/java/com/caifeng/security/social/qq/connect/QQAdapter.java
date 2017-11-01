package com.caifeng.security.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.caifeng.security.social.qq.api.QQ;
import com.caifeng.security.social.qq.api.QQUserInfo;

/**
 * @author Ethan
 * @desc 接口适配器
 * @note QQ:需要适配的接口
 */
public class QQAdapter implements ApiAdapter<QQ> {

	//测试QQ的服务是否可用
	public boolean test(QQ api) {
		return true;
	}

	//设置connection标准的数据集
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();
		//设置昵称
		values.setDisplayName(userInfo.getNickname());
		//设置用户的url
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		//设置主页
		values.setProfileUrl(null);
		//设置提供商Id
		values.setProviderUserId(userInfo.getOpenId());
	}

	public UserProfile fetchUserProfile(QQ api) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateStatus(QQ api, String message) {
		// TODO Auto-generated method stub
	}

}
