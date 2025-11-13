package com.cgpr.visitApp.controllers;


import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import com.cgpr.visitApp.model.Tribunal;
import com.cgpr.visitApp.model.TypeAudience;
 
import com.cgpr.visitApp.repository.repositoryAudience.TribunalRepository;
import com.cgpr.visitApp.repository.repositoryAudience.TypeAudienceRepository;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AudienceReferenceController  {

	
	
	 private final TribunalRepository tribunalRepo;
	    private final TypeAudienceRepository typeAudienceRepo;

	    // ==================== TRIBUNAUX ====================
	    @GetMapping(path = APP_ROOT + "ref/audience/tribunaux", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Tribunal> getTribunaux() {
	        return tribunalRepo.findAll();
	    }

	    @PostMapping(path = APP_ROOT + "ref/audience/tribunaux", consumes = MediaType.APPLICATION_JSON_VALUE)
	    public Tribunal createTribunal(@RequestBody Tribunal tribunal) {
	        return tribunalRepo.save(tribunal);
	    }

	    // ==================== TYPES AUDIENCE ====================
	    @GetMapping(path = APP_ROOT + "ref/audience/types-audience", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<TypeAudience> getTypesAudience() {
	        return typeAudienceRepo.findAll();
	    }

	    @PostMapping(path = APP_ROOT + "ref/audience/types-audience", consumes = MediaType.APPLICATION_JSON_VALUE)
	    public TypeAudience createTypeAudience(@RequestBody TypeAudience typeAudience) {
	        return typeAudienceRepo.save(typeAudience);
	    }
}
