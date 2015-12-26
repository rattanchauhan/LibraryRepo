package com.library.app.common.exceptions;

public class FieldNotValidException extends RuntimeException {
	private static final long serialVersionUID = 6938668678983684689L;

	private String fieldName;

	public FieldNotValidException(final String fieldName, final String message) {
		super(message);
		this.setFieldName(fieldName);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}

}
