package com.cgpr.visitApp.handlers;
 
 
import java.util.List;

import com.cgpr.visitApp.exception.ErrorCodes;

public class HospitalisationException extends RuntimeException {

    private final ErrorCodes errorCode;
    private final List<String> errors;

    public HospitalisationException(ErrorCodes errorCode, List<String> errors) {
        super(errors.isEmpty() ? null : errors.get(0));
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
