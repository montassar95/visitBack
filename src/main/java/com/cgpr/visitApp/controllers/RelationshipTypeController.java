package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.DayDto;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.services.PdfService;
import com.cgpr.visitApp.services.RelationshipTypeService;
import com.ibm.icu.text.ArabicShapingException;
import com.itextpdf.text.DocumentException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RelationshipTypeController {

    private RelationshipTypeService relationshipTypeService;
    private PrisonerPenalRepository prisonerPenalRepository;
    private PdfService pdfService;

    @Autowired
    public RelationshipTypeController(RelationshipTypeService relationshipTypeService, PrisonerPenalRepository prisonerPenalRepository, PdfService pdfService) {
        this.prisonerPenalRepository = prisonerPenalRepository;
        this.relationshipTypeService = relationshipTypeService;
        this.pdfService = pdfService;
    }

    // Endpoint pour sauvegarder une relation
    @PostMapping(path = APP_ROOT + "relationshipTypes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipType>> save(@RequestBody RelationshipTypeDto relationshipTypeDto) {
        return ResponseEntity.ok(relationshipTypeService.save(relationshipTypeDto));
    }

    // Endpoint pour récupérer les relations par ID du prisonnier
    @GetMapping(path = APP_ROOT + "relationshipTypes/idPrisoner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationshipTypeDto> findByPrisonerId(@PathVariable("id") String idPrisoner) {
        return ResponseEntity.ok(relationshipTypeService.findByPrisonerId(idPrisoner));
    }

    // Endpoint pour rechercher les prisonniers entrants par période et emplacement
    @GetMapping(path = APP_ROOT + "relationshipTypes/findPrisonersEnteringByPeriodAndLocation/{startDate}/{endDate}/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findPrisonersEnteringByPeriodandLocation(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
            @PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        // Gérer les dates
        SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateToUpdated = null;
        Date endDateToUpdated = null;
        try {
            startDateToUpdated = originalDateFormat.parse(startDate);
            endDateToUpdated = originalDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateUpdated = newDateFormat.format(startDateToUpdated);
        String endDateUpdated = newDateFormat.format(endDateToUpdated);

        return ResponseEntity.ok(relationshipTypeService.findPrisonersEnteringByPeriodandLocation(startDateUpdated, endDateUpdated, gouvernorat, prison));
    }

    // Endpoint pour rechercher les prisonniers en mutation par période et emplacement
    @GetMapping(path = APP_ROOT + "relationshipTypes/findPrisonersMutatingByPeriodAndLocation/{startDate}/{endDate}/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findPrisonersMutatingByPeriodandLocation(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
            @PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        // Gérer les dates
        SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateToUpdated = null;
        Date endDateToUpdated = null;
        try {
            startDateToUpdated = originalDateFormat.parse(startDate);
            endDateToUpdated = originalDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateUpdated = newDateFormat.format(startDateToUpdated);
        String endDateUpdated = newDateFormat.format(endDateToUpdated);

        return ResponseEntity.ok(relationshipTypeService.findPrisonersMutatingByPeriodandLocation(startDateUpdated, endDateUpdated, gouvernorat, prison));
    }

    // Endpoint pour rechercher les prisonniers quittant par période et emplacement
    @GetMapping(path = APP_ROOT + "relationshipTypes/findPrisonersLeavingByPeriodAndLocation/{startDate}/{endDate}/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findPrisonersLeavingByPeriodandLocation(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
            @PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        // Gérer les dates
        SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateToUpdated = null;
        Date endDateToUpdated = null;
        try {
            startDateToUpdated = originalDateFormat.parse(startDate);
            endDateToUpdated = originalDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateUpdated = newDateFormat.format(startDateToUpdated);
        String endDateUpdated = newDateFormat.format(endDateToUpdated);

        return ResponseEntity.ok(relationshipTypeService.findPrisonersLeavingByPeriodandLocation(startDateUpdated, endDateUpdated, gouvernorat, prison));
    }

    // Endpoint pour rechercher les prisonniers existants sans visite par emplacement
    @GetMapping(path = APP_ROOT + "relationshipTypes/findPrisonersExistingByLocationWithOutVisit/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findPrisonersExistingByLocationWithOutVisit(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return ResponseEntity.ok(relationshipTypeService.findPrisonersExistingByLocationWithOutVisit(gouvernorat, prison));
    }

    // Endpoint pour mettre à jour le statut de résidence des relations
    @GetMapping(path = APP_ROOT + "relationshipTypes/updateRelationshipTypeStatutResidance", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRelationshipTypeStatutResidance() {
        relationshipTypeService.updateRelationshipTypeStatutResidance();
    }

    // Endpoint pour obtenir la matrice de compte pour le tableau de bord
    @GetMapping(path = APP_ROOT + "relationshipTypes/getCountMatrix/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<List<String>> getCountMatrix(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return relationshipTypeService.getCountMatrix(gouvernorat, prison);
    }

    // Endpoint pour rechercher les relations par heure, jour, emplacement et statut "Ouvert"
    @GetMapping(path = APP_ROOT + "relationshipTypes/findByTimeAndDayAndPrisonAndStatutOpen/{time}/{day}/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findByTimeAndDayAndPrisonAndStatutOpen(@PathVariable("time") String time, @PathVariable("day") String day,
            @PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return ResponseEntity.ok(relationshipTypeService.findByTimeAndDayAndPrisonAndStatutO(time, day, gouvernorat, prison));
    }

    // Endpoint pour rechercher les relations par emplacement et statut "Non Ouvert"
    @GetMapping(path = APP_ROOT + "relationshipTypes/findByPrisonAndStatutNotOpen/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findByPrisonAndStatutNotOpen(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return ResponseEntity.ok(relationshipTypeService.findByPrisonAndStatutNotO(gouvernorat, prison));
    }

    // Endpoint pour obtenir la matrice de compte pour le tableau de bord
    @GetMapping(path = APP_ROOT + "relationshipTypes/getCountMatrixForDashboarding/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DayDto>> getCountMatrixForDashboarding(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return ResponseEntity.ok(relationshipTypeService.getCountMatrixForDashboarding(gouvernorat, prison));
    }

    // Endpoint pour rechercher les relations par emplacement, statut de résidence et statut SMS
    @GetMapping(path = APP_ROOT + "relationshipTypes/findByStatuses/{gouvernorat}/{prison}/{statutResidance}/{statutSMS}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> findByStatuses(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison,
            @PathVariable("statutResidance") String statutResidance, @PathVariable("statutSMS") String statutSMS,
            @RequestParam(name = "dateStart") String dateStart,
            @RequestParam(name = "dateEnd") String dateEnd) {
    	
    	
    	
    
    	if (statutResidance.trim().equalsIgnoreCase("withOutVisit")) {
    		
    		  return ResponseEntity.ok(relationshipTypeService.findPrisonersExistingByLocationWithOutVisit(gouvernorat.trim(), prison.trim()));
    	}
        
        
        
    	else {if (statutSMS.trim().equals("WAIT")) {
            statutSMS = null;
        } else {
            statutSMS = statutSMS.trim();
        }
    	}
        return ResponseEntity.ok(relationshipTypeService.findByStatuses(gouvernorat.trim(), prison.trim(), statutResidance.trim(), statutSMS,dateStart ,dateEnd ));
    }

//    // Endpoint pour générer un fichier PDF des relations filtrées
    @GetMapping(path = APP_ROOT + "relationshipTypes/findByStatusesPdf/{gouvernorat}/{prison}/{statutResidance}/{statutSMS}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> findByStatusesPdf(@PathVariable("gouvernorat") String gouvernorat, 
                                                   @PathVariable("prison") String prison, 
                                                   @PathVariable("statutResidance") String statutResidance, 
                                                   @PathVariable("statutSMS") String statutSMS,
                                                   @RequestParam(name = "dateStart") String dateStart,
                                                   @RequestParam(name = "dateEnd") String dateEnd) {

        String nomPrison = prisonerPenalRepository.findPrisonByCode(gouvernorat, prison).getName();
        String titre = buildTitle(gouvernorat, prison, statutResidance, statutSMS);

        byte[] pdfData = null;
        List<RelationshipTypeDto> list = null;
        try {
          if (statutResidance.trim().equalsIgnoreCase ("withOutVisit")) {
        	 System.err.println("avant . "+pdfData==null);
               list= relationshipTypeService.findPrisonersExistingByLocationWithOutVisit(gouvernorat.trim(), prison.trim());
               System.err.println("Le PDF est généré pour WithOutVisit. "+list.size());
                pdfData = pdfService.exportWithOutVisitPdf(list, nomPrison, titre);
                System.err.println("apres . "+pdfData==null);
           } 
            
            else {
            	
                if (statutSMS.trim().equals("WAIT")) {
                	  System.err.println("Le PDF est généré pour WAIT.");
                      statutSMS = null;
                } else {
                	 System.err.println("Le PDF est généré pour READY ou FAILED ou SENT.");
                    statutSMS = statutSMS.trim();
                }

                pdfData = pdfService.exportPdf(relationshipTypeService.findByStatuses(gouvernorat.trim(), prison.trim(), statutResidance.trim(), statutSMS, dateStart, dateEnd), nomPrison, titre);

               
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=enfnat.pdf");

            ResponseEntity<byte[]> response = ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfData);

            return response;
        } catch (DocumentException | IOException | ArabicShapingException e) {
            e.printStackTrace();
            return ResponseEntity.status(444).build();
        }
        // Retournez quelque chose de significatif ici en cas d'erreur.
        
    }

    @GetMapping(path = APP_ROOT + "relationshipTypes/findByTimeAndDayAndPrisonAndStatutOpenPdf/{time}/{day}/{gouvernorat}/{prison}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]>   findByTimeAndDayAndPrisonAndStatutOpenPdf(@PathVariable("time") String time, @PathVariable("day") String day,
            @PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
    	byte[] pdfData = null;
        List<RelationshipTypeDto> list = null;
        String titre="قــــائمة الزيــــارات";
        try {
           
              
        	if (!(day.trim().toString().equals("null".toString()) || day == null || day.trim().isEmpty())) {
    			titre =titre+" "+"ليوم "+day;
    		} 
        	
        	if (!(time.trim().toString().equals("null".toString()) || time == null || time.trim().isEmpty())) {
        		   titre=titre+" "+"على ساعة"+time;
    		} 
    		 
              	
        	String nomPrison = prisonerPenalRepository.findPrisonByCode(gouvernorat, prison).getName();
                  pdfData = pdfService.exportPdf(relationshipTypeService.findByTimeAndDayAndPrisonAndStatutO(time, day, gouvernorat, prison), nomPrison, titre);

                 
             
              
              HttpHeaders headers = new HttpHeaders();
              headers.add("Content-Disposition", "inline; filename=enfnat.pdf");

              ResponseEntity<byte[]> response = ResponseEntity.ok()
                      .headers(headers)
                      .contentType(MediaType.APPLICATION_PDF)
                      .body(pdfData);

              return response;
          } catch (DocumentException | IOException | ArabicShapingException e) {
              e.printStackTrace();
              return ResponseEntity.status(444).build();
          }
        
    }
//    @GetMapping(path = APP_ROOT + "relationshipTypes/findByStatusesPdf/{gouvernorat}/{prison}/{statutResidance}/{statutSMS}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> findByStatusesPdf(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison, @PathVariable("statutResidance") String statutResidance, 
//    		@PathVariable("statutSMS") String statutSMS ,
//    		@RequestParam(name = "dateStart") String dateStart,
//            @RequestParam(name = "dateEnd") String dateEnd) {
//      
//    	
//    	
//    	String nomPrision = prisonerPenalRepository.findPrisonByCode(gouvernorat, prison).getName();
//        String titre = buildTitle(gouvernorat, prison, statutResidance, statutSMS);
//
//        byte[] pdfData = null;
//        List<RelationshipTypeDto> list=null;
//        try {
//        	
//        	
//       
//        	if (statutResidance.trim().equals("WithOutVisit")) {
//        		System.err.println("pdf est dans WithOutVisit");
//        		 pdfData = pdfService.exportPdf(relationshipTypeService.findPrisonersExistingByLocationWithOutVisit(gouvernorat.trim(), prison.trim()), nomPrision, titre);
//      		 
//      	}
//          
//          
//          
//      	else {
//        if (statutSMS.trim().equals("WAIT")) {
//                statutSMS = null;
//                pdfData = pdfService.exportPdf(relationshipTypeService.findByStatuses(gouvernorat.trim(), prison.trim(), statutResidance.trim(), statutSMS,dateStart ,dateEnd ), nomPrision, titre);
//
//            } else {
//                statutSMS = statutSMS.trim();
//                pdfData = pdfService.exportPdf(relationshipTypeService.findByStatuses(gouvernorat.trim(), prison.trim(), statutResidance.trim(), statutSMS,dateStart ,dateEnd ), nomPrision, titre);
//
//            }
//        }
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "inline; filename=enfnat.pdf");
//
//            ResponseEntity<byte[]> response = ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(pdfData);
//
//            return response;
//        } catch (DocumentException | IOException | ArabicShapingException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(444).build();
//        }
//    }

    // Endpoint pour mettre à jour le statut SMS des relations
    @PostMapping(path = APP_ROOT + "relationshipTypes/updateStatutSMSToReady")
    public ResponseEntity<Void> updateStatutSMS(@RequestParam("statut") String statut, @RequestBody List<RelationshipTypeDto> relationshipTypeDtoList) {
        try {
            relationshipTypeService.updateStatutSMSToReady(statut, relationshipTypeDtoList);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String buildTitle(String gouvernorat, String prison, String statutResidance, String statutSMS) {
        String resultat = "";
        System.err.println(statutResidance.trim());
        switch (statutResidance.trim().toString().toUpperCase()) {
            case "E":
                resultat = "قائمة الإعلام بالإيداع ";
                break;
            case "M":
                resultat = "قائمة الإعلام بالنقل ";
                break;
            case "O":
                resultat = "قائمة الإعلام بالزيارات ";
                break;
            case "L":
                resultat = "قائمة الإعلام بالسرحات ";
                break;
            case "WITHOUTVISIT":
                resultat = "قــــائمة المــــودعين بدون موعــد زيــــارة  ";
                 
                break;
            default:
                resultat = "1 ";
        }

        String etat = "";
        if(!statutResidance.trim().toString().toString().trim().equalsIgnoreCase("WithOutVisit")) {
        	switch (statutSMS) {
            case "WAIT":
                etat = "في إنتظار التأكيد ";
                break;
            case "READY":
                etat = "جارية الإرسال ";
                break;
            case "SENT":
                etat = "المرسلة ";
                break;
            case "FAILED":
                etat = "التي لم يتم إرسلها بنجاح ";
                break;
            default:
                resultat = "  ";
        }
        }
        
        return resultat + " " + etat + " ";
    }
}
