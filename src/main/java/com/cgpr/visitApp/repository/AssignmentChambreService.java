package com.cgpr.visitApp.repository;

 

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.cgpr.visitApp.dto.AssignmentChambreDto;
import com.cgpr.visitApp.dto.CentreWithTotalDto;
import com.cgpr.visitApp.dto.ChambreWithTotalDto;
import com.cgpr.visitApp.dto.ComplexeWithTotalDto;
import com.cgpr.visitApp.dto.PavillonWithTotalDto;
import com.cgpr.visitApp.dto.PrisonWithTotalDto;
import com.cgpr.visitApp.model.AssignmentChambre;

public interface AssignmentChambreService {

    AssignmentChambreDto save(AssignmentChambreDto dto);

    AssignmentChambreDto update(AssignmentChambreDto dto);

    List<AssignmentChambreDto> findByPrisoner(String prisonerId);

    void delete(Long id);
    
    public List<PrisonWithTotalDto> getPrisonsWithTotals();
    public List<CentreWithTotalDto> getCentresWithActiveAffectations(Long prisonId) ;
    public List<ComplexeWithTotalDto> getComplexesWithActiveAffectations(Long prisonId);
    public List<PavillonWithTotalDto> getPavillonsWithActiveAffectations(Long complexeId);
    public List<ChambreWithTotalDto> getChambresWithActiveAffectations(Long pavillonId);
    
    public AssignmentChambreDto findByPrisonerAndDate(
    		String prisonerId,
             LocalDate date);
    
    
  
}
