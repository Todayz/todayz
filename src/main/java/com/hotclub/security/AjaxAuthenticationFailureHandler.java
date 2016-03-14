package com.hotclub.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("ajaxAuthenticationFailureHandler")
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("failure", true);

		// {"success" : true, "returnUrl" : "..."}
		String jsonString = mapper.writeValueAsString(map);
		OutputStream out = response.getOutputStream();
		out.write(jsonString.getBytes());
		/*ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("failure", true);

		// {"success" : true, "returnUrl" : "..."}
		String jsonString = mapper.writeValueAsString(map);
		response.sendError(arg0);
		OutputStream out = response.getOutputStream();
		out.write(jsonString.getBytes());*/
	}
}
