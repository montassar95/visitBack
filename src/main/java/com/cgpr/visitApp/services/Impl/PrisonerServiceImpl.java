package com.cgpr.visitApp.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.exception.EntityNotFoundException;
import com.cgpr.visitApp.exception.ErrorCodes;
import com.cgpr.visitApp.exception.InvalidEntityException;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.services.PrisonerService;
import com.cgpr.visitApp.validator.PrisonerValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrisonerServiceImpl implements PrisonerService {

	private PrisonerRepository prisonerRepository;
	
	private PrisonerPenalRepository prisonerPenalRepository;

	@Autowired
	public PrisonerServiceImpl(PrisonerRepository prisonerRepository,  PrisonerPenalRepository prisonerPenalRepository) {

		this.prisonerRepository = prisonerRepository;
		this.prisonerPenalRepository=prisonerPenalRepository;
	}

	@Override
	public PrisonerDto save(PrisonerDto prisonerDto) {

		List<String> errors = PrisonerValidator.validate(prisonerDto);
		if (!errors.isEmpty()) {
			log.error("Person is not valid {}", prisonerDto);
			throw new InvalidEntityException("Person is not valid", ErrorCodes.Prisoner_Not_Valid, errors);
		}

		return PrisonerDto.fromEntity(prisonerRepository.save(PrisonerDto.toEntity(prisonerDto)));
	}

	@Override
	public PrisonerDto findById(String id) {
		if (id == null) {
			log.error("Prisoner id is null");
			return null;
		}
		//Optional<Prisoner> prisoner = prisonerRepository.findById(id);
		Optional<PrisonerDto> prisonerDto = prisonerPenalRepository.findById(id);
		
		if (prisonerDto.isPresent()) {
			//PrisonerDto prisonerDto = PrisonerDto.fromEntity(prisoner.get());
			return prisonerDto.get();
		} else {
			throw new EntityNotFoundException("Aucun client avec l'ID " + id, ErrorCodes.Prisoner_Not_Found);
		}

	}

	@Override
	public List<PrisonerDto> findAll() {

 		return prisonerRepository.findAll().stream().map(PrisonerDto::fromEntity)
 	   .collect(Collectors.toList());
	}

	@Override
	public void delete(String id) {
		if (id == null) {
			log.error("Person id is null");
			return;
		}

		prisonerRepository.deleteById(id);

	}

	@Override
	public List<PrisonDto> findAllPrisons() {
		// TODO Auto-generated method stub
		return  prisonerPenalRepository.findAllPrisons();
	}
}
