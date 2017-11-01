package com.caifeng.security.validate.code;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.caifeng.security.properties.SecurityProperties;
import com.caifeng.security.validate.code.sms.SmsCodeSender;

@RestController
public class ValidateCodeController {

	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;

	@Autowired
	private ValidateCodeGenerator smsCodeGenerator;
	
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@GetMapping("/code/image")
	public void create(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//生成验证码
		ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(request);
		//保存到session中
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		//输出到前台
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}
	
	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletRequestBindingException{
		//生成验证码
		ValidateCode smsCode = smsCodeGenerator.generate(request);
		//保存到session中
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
		//不同的短信供应商，声明为不同接口
		String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
		smsCodeSender.sendSmsCode(mobile, smsCode.getCode());
	}
}
