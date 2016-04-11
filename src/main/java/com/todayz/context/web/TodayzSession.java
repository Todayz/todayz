package com.todayz.context.web;

import javax.servlet.http.HttpSession;

import com.todayz.security.UserDetailsImpl;

public class TodayzSession {

	public static final String USER_KEY = "userInfo";

	private HttpSession session;

	// http://apache-commons.680414.n4.nabble.com/commons-lang3-NullArgumentException-missing-td4222254.html
	private TodayzSession(HttpSession session) {
		if (session == null) {
			throw new NullPointerException("session");
		}

		this.session = session;
	}

	public static TodayzSession createSession(HttpSession session) {
		return new TodayzSession(session);
	}

	public boolean hasAttribute(String name) {
		return session.getAttribute(name) != null;
	}

	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		session.removeAttribute(name);
	}

	public UserDetailsImpl getUser() {
		return (UserDetailsImpl) session.getAttribute(USER_KEY);
	}
}
