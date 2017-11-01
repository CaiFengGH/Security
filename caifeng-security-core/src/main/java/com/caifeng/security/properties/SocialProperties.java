package com.caifeng.security.properties;

public class SocialProperties {
	
	private QQProperties qqProperties = new QQProperties();
	
	//SocialAuthenticationFilter中的默认的url
	private String filterProcessesUrl = "/auth";

	public QQProperties getQQProperties() {
		return qqProperties;
	}

	public void setQQProperties(QQProperties qqProperties) {
		this.qqProperties = qqProperties;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
}
