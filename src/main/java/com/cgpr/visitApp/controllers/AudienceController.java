package com.cgpr.visitApp.controllers;

 

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cgpr.visitApp.dto.AudienceDto;
import com.cgpr.visitApp.services.AudienceService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AudienceController {

    @Autowired
    private AudienceService audienceService;

    // 🔹 Sauvegarder une nouvelle audience ou mettre à jour
    @PostMapping(path = APP_ROOT + "audiences/save", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AudienceDto> saveAudience(@RequestBody AudienceDto audienceDto) {
        try {
            AudienceDto saved = audienceService.save(audienceDto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔹 Récupérer toutes les audiences d’un prisonnier
    @GetMapping(path = APP_ROOT + "audiences/prisoner/{prisonerId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AudienceDto>> getAudiencesByPrisoner(@PathVariable String prisonerId) {
        try {
            List<AudienceDto> audiences = audienceService.findByPrisoner(prisonerId);
            if (audiences.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(audiences);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
 // 🔹 Mettre à jour une audience existante
    @PutMapping(path = APP_ROOT + "audiences/update/{id}", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AudienceDto> updateAudience(
            @PathVariable Long id,
            @RequestBody AudienceDto audienceDto) {
        try {
            AudienceDto updated = audienceService.update(id, audienceDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    

    // Supprimer une hospitalisation
    @DeleteMapping(path = APP_ROOT + "audiences/delete/{id}")
    public ResponseEntity<Void> deleteHospitalisation(@PathVariable Long id) {
        try {
        	audienceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

