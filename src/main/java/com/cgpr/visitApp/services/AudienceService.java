package com.cgpr.visitApp.services;

 

import java.util.List;

import com.cgpr.visitApp.dto.AudienceDto;

public interface AudienceService {
    AudienceDto save(AudienceDto audienceDto);
      List<AudienceDto> findByPrisoner(String prisonerId);
      
      
  
	void delete(Long id);
	 
	 public AudienceDto update(Long id, AudienceDto audienceDto);
}
