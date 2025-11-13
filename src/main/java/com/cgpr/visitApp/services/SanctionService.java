package com.cgpr.visitApp.services;

 
import java.util.List;

import com.cgpr.visitApp.dto.SanctionDto;

public interface  SanctionService {

	SanctionDto save(SanctionDto sanction);

    

   

    List<SanctionDto> findByPrisoner(String prisonerId);
 
}
