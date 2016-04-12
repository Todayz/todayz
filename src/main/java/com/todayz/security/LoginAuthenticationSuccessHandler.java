package com.todayz.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("loginAuthenticationSuccessHandler")
@Slf4j
public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

		HttpSession session = request.getSession();
		// jsp page ==> ${sessionScope.userInfo.id} ${userInfo.id}
		session.setAttribute("userInfo", user);

		log.info("authentication success. {}", user.getUsername());
		/*
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * map.put("success", true);
		 * 
		 * // {"success" : true, "returnUrl" : "..."} String jsonString =
		 * mapper.writeValueAsString(map); ${sessionScope.userInfo.id}
		 * ${userInfo.id }
		 * 
		 * OutputStream out = response.getOutputStream();
		 * out.write(jsonString.getBytes());
		 */
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
