package com.caifeng.security.properties;

public class BrowserProperties {

	private String signUpUrl = "/signup.html";
	
	private String login = "/login.html";

	private LoginType loginType = LoginType.JSON;
	
	private int rememberMeSeconds = 3600;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}
}
