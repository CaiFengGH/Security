package com.caifeng.security.browser;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import com.caifeng.security.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.caifeng.security.properties.SecurityProperties;
import com.caifeng.security.validate.code.SmsCodeFilter;
import com.caifeng.security.validate.code.ValidateCodeFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder(){
		//根据加密方式不同获取的encoder
		return new BCryptPasswordEncoder();
	};

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private SimpleUrlAuthenticationFailureHandler myAuthenticationFailureHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig; 
	
	@Bean
	public PersistentTokenRepository tokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//第二从启动时设置为false
		tokenRepository.setCreateTableOnStartup(false);
		return tokenRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		
		validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
		
		validateCodeFilter.setSecurityProperties(securityProperties);
		
		validateCodeFilter.afterPropertiesSet();
		
		
		//验证码逻辑
		SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		
		smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
		
		smsCodeFilter.setSecurityProperties(securityProperties);
		
		smsCodeFilter.afterPropertiesSet();
		
		//基于security的基本配置
//		http.httpBasic()
		http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			//表单登录即认证
			.formLogin()
				//指定登录页面
				.loginPage("/authentication/require")
				//表单登录时UsernamePasswordAuthenticationFilter处理这个请求
				.loginProcessingUrl("/authentication/form")
				//表单登陆后使用定义的处理器
				.successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailureHandler)
			.and()
				.rememberMe()
				.tokenRepository(tokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
			//且	
			.and()
				.authorizeRequests()
				//给予以下url免身份认证
				.antMatchers("/authentication/require",
					//以html结尾的页面放权
					securityProperties.getBrowser().getLogin(),
					//图形验证码授权
					"/code/image").permitAll()
					//授权		
				.anyRequest()
				.authenticated()
			.and()
				//失去服务，跨站请求伪造
				.csrf().disable()
				.apply(smsCodeAuthenticationSecurityConfig);
		
	}
	
}
