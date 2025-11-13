package com.cgpr.visitApp.handlers;
 

import java.util.ArrayList;
import java.util.List;

import com.cgpr.visitApp.exception.ErrorCodes;

import lombok.Getter;

@Getter
 

	public class AssignmentChambreException extends RuntimeException {
	    private final ErrorCodes errorCode;
	    private final List<String> errors;

	    public AssignmentChambreException(ErrorCodes errorCode, String message) {
	        super(message);
	        this.errorCode = errorCode;
	        this.errors = new ArrayList<>();
	    }

	    public AssignmentChambreException(ErrorCodes errorCode, String message, List<String> errors) {
	        super(message);
	        this.errorCode = errorCode;
	        this.errors = errors;
	    }
	}

