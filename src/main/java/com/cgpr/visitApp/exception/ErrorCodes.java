package com.cgpr.visitApp.exception;

 
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor 
public enum ErrorCodes {
    Prisoner_Not_Found(1000),
	Prisoner_Not_Valid(1001),
	Personelle_Not_Found(2000),
	 Hospitalisation_Error(3000); // code pour les erreurs hospitalisation
    private final Integer code;
}
