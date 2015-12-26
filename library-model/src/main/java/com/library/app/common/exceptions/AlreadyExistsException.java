package com.library.app.common.exceptions;

public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7618168200268804889L;
	private String fieldName;

	public AlreadyExistsException(final String fieldName, final String message) {
		super(message);
		this.setFieldName(fieldName);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
