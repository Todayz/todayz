package com.hotclub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class LoginController {
/*
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("httpSessionSecurityContextRepository")
	private SecurityContextRepository repository;

	@RequestMapping(value = "/login.ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

		ModelMap map = new ModelMap();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		try {
			// 로그인
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			repository.saveContext(SecurityContextHolder.getContext(), request, response);

			map.put("success", true);
		} catch (BadCredentialsException e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;
	}*/

	/**
	 * 로그인 하기 전의 요청했던 URL을 알아낸다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 *//*
		 * private String getReturnUrl(HttpServletRequest request,
		 * HttpServletResponse response) { RequestCache requestCache = new
		 * HttpSessionRequestCache(); SavedRequest savedRequest =
		 * requestCache.getRequest(request, response); if (savedRequest == null)
		 * { return request.getSession().getServletContext().getContextPath(); }
		 * return savedRequest.getRedirectUrl(); }
		 */
}