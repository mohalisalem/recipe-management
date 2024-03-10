package com.cerbaresearch.recipe.management.web.exception;

public class EntityAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public final String message;

	public EntityAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public EntityAlreadyExistsException(String message) {
		super(message);
		this.message = message;
	}

}
