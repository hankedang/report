package com.centrixlink.exception;

public class SessionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SessionException () {
		super();
	}
	
	public SessionException (String message) {
		super(message);
	}
	
	public SessionException (String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getMessage () {
		return "Session Invalid";
	}
	
}
