package com.cgpr.visitApp.controllers;

 

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.AssignmentChambreDto;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.handlers.AssignmentChambreException;
import com.cgpr.visitApp.handlers.ErrorDto;
import com.cgpr.visitApp.repository.AssignmentChambreService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(APP_ROOT + "assignment-chambre")
public class AssignmentChambreController {

    @Autowired
    private AssignmentChambreService assignmentService;

    // Créer une nouvelle affectation
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AssignmentChambreDto dto) {
    	
    	
    	System.err.println(dto.toString());
//        try {
            AssignmentChambreDto saved = assignmentService.save(dto);
            return ResponseEntity.ok(saved);
//        } catch (AssignmentChambreException e) {
//            ErrorDto error = ErrorDto.builder()
//                    .code(e.getErrorCode()) // doit retourner un ErrorCodes
//                    .httpCode(HttpStatus.BAD_REQUEST.value())
//                    .message(e.getMessage())
//                    .errors(e.getErrors())
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        } catch (Exception e) {
//            ErrorDto error = ErrorDto.builder()
//                    .code(null)
//                    .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .message("Erreur serveur inattendue")
//                    .errors(Arrays.asList(e.getMessage()))
//                    .build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
    }

    // Mettre à jour une affectation existante
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AssignmentChambreDto dto) {
//        try {
            dto.setId(id);
            AssignmentChambreDto updated = assignmentService.update(dto);
            return ResponseEntity.ok(updated);
//        } catch (AssignmentChambreException e) {
//            ErrorDto error = ErrorDto.builder()
//                    .code(e.getErrorCode())
//                    .httpCode(HttpStatus.BAD_REQUEST.value())
//                    .message(e.getMessage())
//                    .errors(e.getErrors())
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        } catch (Exception e) {
//            ErrorDto error = ErrorDto.builder()
//                    .code(null)
//                    .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .message("Erreur serveur inattendue")
//                    .errors(Arrays.asList(e.getMessage()))
//                    .build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
    }

    // Récupérer toutes les affectations d’un prisonnier
    @GetMapping("/prisoner/{prisonerId}")
    public ResponseEntity<List<AssignmentChambreDto>> findByPrisoner(@PathVariable String prisonerId) {
        try {
            List<AssignmentChambreDto> list = assignmentService.findByPrisoner(prisonerId);
            if (list.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            ErrorDto error = ErrorDto.builder()
                    .code(null)
                    .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erreur serveur inattendue")
                    .errors(Arrays.asList(e.getMessage()))
                    .build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            return  ResponseEntity.badRequest().build();
        }
    }

    // Supprimer une affectation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            assignmentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (AssignmentChambreException e) {
            ErrorDto error = ErrorDto.builder()
                    .code(e.getErrorCode())
                    .httpCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .errors(e.getErrors())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            ErrorDto error = ErrorDto.builder()
                    .code(null)
                    .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erreur serveur inattendue")
                    .errors(Arrays.asList(e.getMessage()))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    
    
    
 

    @GetMapping(path = "/findByPrisonerAndDate/{prisonerId}/{dateParam}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssignmentChambreDto> findByPrisonerAndDate(
    		@PathVariable String prisonerId,
            @PathVariable("dateParam") String dateParam) {

        // Formatter pour parser la date reçue (yyyy-MM-dd)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate;
     

        try {
            
           localDate = LocalDate.parse(dateParam, inputFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        // Appel du service directement avec LocalDate
        AssignmentChambreDto assignmentChambreDto = assignmentService.findByPrisonerAndDate(prisonerId, localDate);

        return ResponseEntity.ok(assignmentChambreDto);
    }

    
}
