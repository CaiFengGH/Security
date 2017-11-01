package com.caifeng.security.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author Ethan
 * @desc 覆盖其默认的路径/auth
 */
public class MySpringSocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessesUrl; 
	
	public MySpringSocialConfigurer(String filterProcessesUrl){
		this.filterProcessesUrl = filterProcessesUrl;
	}
	 
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}
	
}
