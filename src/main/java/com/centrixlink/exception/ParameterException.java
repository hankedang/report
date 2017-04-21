package com.centrixlink.exception;

public class ParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static final String TYPE = "[Param Error]";
	
	public ParameterException () {
		super();
	}
	
	public ParameterException (String message) {
		super(TYPE + message);
	}
	
	public ParameterException (String message, Throwable cause) {
		super(TYPE + message, cause);
	}

}
