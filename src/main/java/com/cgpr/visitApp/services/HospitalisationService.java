package com.cgpr.visitApp.services;

 

import java.util.List;
import com.cgpr.visitApp.dto.HospitalisationDto;

public interface HospitalisationService {

    // Sauvegarder une hospitalisation
    HospitalisationDto save(HospitalisationDto hospitalisation);

    // Récupérer toutes les hospitalisations d’un prisonnier
    List<HospitalisationDto> findByPrisoner(String prisonerId);
    
    
    HospitalisationDto update(HospitalisationDto dto);
    void delete(Long id);

}
