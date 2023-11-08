package com.cgpr.visitApp.exception;

 
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor 
public enum ErrorCodes {
    Prisoner_Not_Found(1000),
	Prisoner_Not_Valid(1001);
     
    private final Integer code;
}
