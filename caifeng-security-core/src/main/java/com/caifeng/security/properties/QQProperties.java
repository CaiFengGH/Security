package com.caifeng.security.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

public class QQProperties extends SocialProperties {

	//服务提供商的唯一ID
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}
