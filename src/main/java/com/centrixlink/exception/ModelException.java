package com.centrixlink.exception;

public class ModelException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ModelException () {
		super();
	}
	
	public ModelException (String message) {
		super(message);
	}
	
	public ModelException (String message, Throwable cause) {
		super(message, cause);
	}

}
