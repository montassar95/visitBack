package com.cgpr.visitApp.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cgpr.visitApp.exception.EntityNotFoundException;
import com.cgpr.visitApp.exception.InvalidEntityException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	// listener for EntityNotFoundException
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception, WebRequest webRequest) {

		final HttpStatus notFound = HttpStatus.NOT_FOUND;
		final ErrorDto errorDto = ErrorDto.builder().code(exception.getErrorCode()).httpCode(notFound.value())
				.message(exception.getMessage()).build();

		return new ResponseEntity<>(errorDto, notFound);

	}

	// listener for EntityNotFoundException
	@ExceptionHandler(InvalidEntityException.class)
	public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception, WebRequest webRequest) {

		final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		final ErrorDto errorDto = ErrorDto.builder().code(exception.getErrorCode()).httpCode(badRequest.value())
				.message(exception.getMessage()).errors(exception.getErrors()).build();

		return new ResponseEntity<>(errorDto, badRequest);

	}
}
