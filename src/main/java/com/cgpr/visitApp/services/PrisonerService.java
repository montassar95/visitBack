package com.cgpr.visitApp.services;

import java.util.List;

import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;

public interface PrisonerService {

	PrisonerDto save(PrisonerDto prisonerDto);

	PrisonerDto findById(String id);

	List<PrisonerDto> findAll();
	
	List<PrisonDto> findAllPrisons();

	void delete(String  id);

}
