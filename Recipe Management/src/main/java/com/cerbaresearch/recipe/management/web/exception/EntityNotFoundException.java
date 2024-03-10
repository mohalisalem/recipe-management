package com.cerbaresearch.recipe.management.web.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public final String message;

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public EntityNotFoundException(String message) {
		super(message);
		this.message = message;
	}

}
