package com.library.app.common.exceptions;

public class CategoryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8436337313877972458L;

	public CategoryNotFoundException(final String message) {
		super(message);
	}

}
