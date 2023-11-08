package com.cgpr.visitApp.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

	@Getter
	private ErrorCodes errorCode;

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause, ErrorCodes errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public EntityNotFoundException(String message, ErrorCodes errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
