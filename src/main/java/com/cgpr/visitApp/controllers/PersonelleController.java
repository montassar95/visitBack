package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.PersonelleDto;
import com.cgpr.visitApp.dto.SigninDto;
import com.cgpr.visitApp.services.PersonelleService;
 
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
 
public class PersonelleController {

	private PersonelleService personelleService;

	@Autowired
	public PersonelleController(PersonelleService personelleService) {

		this.personelleService = personelleService;
	}

	 
	
	
	
	@PostMapping(path = APP_ROOT
			+ "personelle/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 
	public ResponseEntity<PersonelleDto> save(@RequestBody SigninDto signinDto) {
		if (signinDto.getLogin() == null || signinDto.getLogin().trim().isEmpty() ||
	            signinDto.getPwd() == null || signinDto.getPwd().trim().isEmpty()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
		
		 // Appel du service pour trouver l'utilisateur
        PersonelleDto personelleDto = personelleService.findByLoginAndPwd(signinDto.getLogin().trim(), signinDto.getPwd().trim());

        // Vérification si l'utilisateur est trouvé
        if (personelleDto != null) {
            return ResponseEntity.ok(personelleDto);
        } else {
            return ResponseEntity.notFound().build();
        }
		

	}

	 

}
