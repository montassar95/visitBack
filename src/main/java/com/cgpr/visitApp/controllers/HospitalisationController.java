package com.cgpr.visitApp.controllers;

 

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cgpr.visitApp.dto.HospitalisationDto;
import com.cgpr.visitApp.handlers.ErrorDto;
import com.cgpr.visitApp.handlers.HospitalisationException;
import com.cgpr.visitApp.services.HospitalisationService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HospitalisationController {

    @Autowired
    private HospitalisationService hospitalisationService;

    // Sauvegarder une nouvelle hospitalisation  
    @PostMapping(path = APP_ROOT + "hospitalisations/save", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> saveHospitalisation(@RequestBody HospitalisationDto hospitalisation) {
    	
    	
   try {
       HospitalisationDto saved = hospitalisationService.save(hospitalisation);
       return ResponseEntity.ok(saved);
   } catch (HospitalisationException e) {
       ErrorDto errorDto = ErrorDto.builder()
           .code(e.getErrorCode())
           .httpCode(HttpStatus.BAD_REQUEST.value())
           .message(e.getMessage())
           .errors(e.getErrors())
           .build();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
   } catch (Exception e) {
       ErrorDto errorDto = ErrorDto.builder()
           .code(null)
           .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
           .message("Erreur serveur inattendue")
           .errors(Arrays.asList(e.getMessage()))
           .build();
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
   }
}

    
    
    // Mettre à jour une hospitalisation existante
 // Mettre à jour une hospitalisation existante
    @PutMapping(path = APP_ROOT + "hospitalisations/update/{id}", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHospitalisation(
            @PathVariable Long id, 
            @RequestBody HospitalisationDto hospitalisation) {
        try {
            hospitalisation.setId(id); // fixer l'ID pour l'update
            HospitalisationDto updated = hospitalisationService.update(hospitalisation);
            return ResponseEntity.ok(updated);
        } catch (HospitalisationException e) {
            // Erreurs métier custom
            ErrorDto errorDto = ErrorDto.builder()
                .code(e.getErrorCode())
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .errors(e.getErrors())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
        } catch (Exception e) {
            // Erreur serveur inattendue
            ErrorDto errorDto = ErrorDto.builder()
                .code(null)
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erreur serveur inattendue")
                .errors(Arrays.asList(e.getMessage()))
                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
        }
    }

    
    

    // Récupérer toutes les hospitalisations d’un prisonnier
    @GetMapping(path = APP_ROOT + "hospitalisations/prisoner/{prisonerId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HospitalisationDto>> getHospitalisationsByPrisoner(@PathVariable String prisonerId) {
        try {
            List<HospitalisationDto> hospitalisations = hospitalisationService.findByPrisoner(prisonerId);
            if (hospitalisations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(hospitalisations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    
    

    // Supprimer une hospitalisation
    @DeleteMapping(path = APP_ROOT + "hospitalisations/delete/{id}")
    public ResponseEntity<Void> deleteHospitalisation(@PathVariable Long id) {
        try {
            hospitalisationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}

