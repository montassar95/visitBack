package com.cgpr.visitApp.controllers;

 

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.SanctionDto;
import com.cgpr.visitApp.services.SanctionService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
 
public class SanctionController {

    @Autowired
    private SanctionService sanctionService;

    // Sauvegarder une nouvelle sanction ou mettre à jour
 
    @PostMapping(path = APP_ROOT + "sanctions/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SanctionDto> saveSanction(@RequestBody SanctionDto sanction) {
        try {
        	System.out.println("sanction : ");
        	System.out.println(sanction);
        	 SanctionDto saved = sanctionService.save(sanction);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    
    

    // Récupérer toutes les sanctions d’un prisonnier
			    @GetMapping(path = APP_ROOT + "sanctions/prisoner/{prisonerId}", 
			            produces = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<?> getSanctionsByPrisoner(@PathVariable String prisonerId) {
			    try {
			        System.out.println(">>> ID reçu = " + prisonerId);
			        List<SanctionDto> sanctions = sanctionService.findByPrisoner(prisonerId);
			        System.out.println(">>> Nombre de sanctions trouvées = " 
			                           + (sanctions != null ? sanctions.size() : "null"));
			
			        if (sanctions == null || sanctions.isEmpty()) {
			            return ResponseEntity.noContent().build();
			        }
			
			        return ResponseEntity.ok(sanctions);
			    } catch (Exception e) {
			        e.printStackTrace();
			        return ResponseEntity
			                .status(HttpStatus.INTERNAL_SERVER_ERROR)
			                .body("Erreur backend : " + e.getMessage());
			    }
}


}
