package com.caifeng.security.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

	public void sendSmsCode(String mobile, String code) {
		System.out.println("向手机"+mobile+"发送验证码"+code);
	}
}
