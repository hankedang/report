package com.centrixlink.exception;

import com.centrixlink.sso.bean.User;

public class AuthException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public AuthException () {
		super();
	}
	
	public AuthException (User user) {
		this();
		this.user = user;
	}
	
	public AuthException (String message) {
		super(message);
	}
	
	public AuthException (String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getMessage () {
		return user.toString();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
