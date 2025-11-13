package com.cgpr.visitApp.services.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgpr.visitApp.dto.PersonelleDto;
import com.cgpr.visitApp.exception.EntityNotFoundException;
import com.cgpr.visitApp.exception.ErrorCodes;
import com.cgpr.visitApp.model.Personelle;
import com.cgpr.visitApp.repository.PersonelleRepository;
import com.cgpr.visitApp.services.PersonelleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonelleServiceImpl implements PersonelleService {
	
	
       private PersonelleRepository personelleRepository;
	
 

	@Autowired
	public PersonelleServiceImpl(PersonelleRepository personelleRepository) {

		this.personelleRepository = personelleRepository;
	 
	}
	
	@Override
	public PersonelleDto findByLoginAndPwd(String login, String pwd) {
		// TODO Auto-generated method stub
		
    Optional<Personelle> personelle = personelleRepository.findByLoginAndPwd(login,  pwd);
		
		if (personelle.isPresent()) {
			//PrisonerDto prisonerDto = PrisonerDto.fromEntity(prisoner.get());
			return PersonelleDto.fromEntity(personelle.get());
		} else {
			throw new EntityNotFoundException("Aucun personelle avec  login " + login +" et pwd = "+ pwd ,  ErrorCodes.Personelle_Not_Found);
		}
	 
	}

	 
	
 

	
}
