package com.todayz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todayz.security.LoginAuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoginAuthenticationSuccessHandler successHandler;

	// @Autowired
	// private AjaxAuthenticationFailureHandler failureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// csrf 에 대한 설명 : http://whiteship.me/?p=13833
		http
				// formLogin 관련
				.formLogin().loginPage("/pages/signin").defaultSuccessUrl("/pages/index")
				// 인증 성공시와 인증 실패 시 ajax 요청을 받기 위해 사용한 handler
				.successHandler(successHandler)// .failureHandler(failureHandler)
				.loginProcessingUrl("/loginProcess")
				// logout
				.and().logout().logoutUrl("/logoutProcess").logoutSuccessUrl("/pages/signin").and().authorizeRequests()
				// resources
				// .antMatchers("/webjars/**","/static/**").permitAll()
				// sign page
				.antMatchers("/pages/signin", "/pages/signup").permitAll()
				// TODO 주소 규칙 변경
				// members
				.antMatchers(HttpMethod.POST, "/members").permitAll()
				// members
				.antMatchers("/members/admin/**").hasRole("ADMIN")
				// clubs TODO ROLE_MANAGER 생성 할 예정. 앞으로 club 는 매니저만 만들수 있도록 변경.
				// .antMatchers("/clubs", "/clubs/**").hasRole("USER")
				// menus TODO 해당 클럽에 관리 권한이 있는 사람만 수정가능하도록 변경.
				// etc
				.antMatchers("/pages/**", "/").authenticated().anyRequest().permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/console/**");
	}
}
