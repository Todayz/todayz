package com.hotclub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	// @Autowired
	// private AjaxAuthenticationSuccessHandler successHandler;

	// @Autowired
	// private AjaxAuthenticationFailureHandler failureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// csrf 에 대한 설명 : http://whiteship.me/?p=13833
		// http.csrf().disable();
		// http.httpBasic();
		http
				// formLogin 관련
				.formLogin().loginPage("/pages/signin").defaultSuccessUrl("/pages/index")
				// 인증 성공시와 인증 실패 시 ajax 요청을 받기 위해 사용한 handler
				// .successHandler(successHandler).failureHandler(failureHandler)
				.loginProcessingUrl("/loginProcess")
				// logout
				.and().logout().permitAll().logoutSuccessUrl("/").and().authorizeRequests()
				// resources
				.antMatchers("/webjars/**","/static/**").permitAll()
				// sign page
				.antMatchers("/pages/signin", "/pages/signup").permitAll()
				// members
				.antMatchers(HttpMethod.POST, "/members").permitAll()
				// members
				.antMatchers("/members", "/members/**").hasRole("USER")
				// etc
				.antMatchers("/**").authenticated();
	}
}
