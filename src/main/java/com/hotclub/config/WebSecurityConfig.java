package com.hotclub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hotclub.security.AjaxAuthenticationFailureHandler;
import com.hotclub.security.AjaxAuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AjaxAuthenticationSuccessHandler successHandler;

	@Autowired
	private AjaxAuthenticationFailureHandler failureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// csrf 에 대한 설명 : http://whiteship.me/?p=13833
		http.csrf().disable();
		http.httpBasic();
		http
				// formLogin 관련
				.formLogin().loginPage("/pages/login.html")
				// 인증 성공시와 인증 실패 시 ajax 요청을 받기 위해 사용한 handler
				.successHandler(successHandler).failureHandler(failureHandler).loginProcessingUrl("/loginProcess")
				// logout 관련
				.and().logout().logoutSuccessUrl("/").and().authorizeRequests()
				// login.html
				.antMatchers("/pages/login.html").permitAll()
				// members
				.antMatchers("/members", "/members/**").hasRole("USER")
				// index.html
				.antMatchers("/pages/index.html").authenticated().anyRequest().permitAll();
	}
}
