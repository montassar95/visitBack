package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.dto.RelationshipTypeAmenDto;
import com.cgpr.visitApp.dto.VisitorDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenVisitorDto;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.VisiteursTime;
import com.cgpr.visitApp.model.Visitor;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.RelationshipTypeRepository;
import com.cgpr.visitApp.repository.VisiteursTimeRepository;
import com.cgpr.visitApp.repository.VisitorRepository;
import com.cgpr.visitApp.services.RelationshipTypeService;
import com.cgpr.visitApp.services.VisitorService;

import reactor.core.publisher.Mono;

 
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
 public class VisitorController {

	private VisitorService visitorService;
	private VisiteursTimeRepository visiteursTimeRepository;
	private RelationshipTypeService relationshipTypeService;
	private PrisonerPenalRepository prisonerPenalRepository;
	private RelationshipTypeRepository relationshipTypeRepository;
	private  VisitorRepository  visitorRepository;
	private PrisonerRepository prisonerRepository;
	
	
	@Autowired
	public VisitorController(VisitorService visitorService,VisiteursTimeRepository visiteursTimeRepository, RelationshipTypeRepository relationshipTypeRepository,
			RelationshipTypeService relationshipTypeService ,PrisonerPenalRepository prisonerPenalRepository , VisitorRepository visitorRepository,
			PrisonerRepository prisonerRepository) { 

		this.visitorService = visitorService;
		this.visiteursTimeRepository=visiteursTimeRepository;
		this.relationshipTypeService=relationshipTypeService;
		this.prisonerPenalRepository=prisonerPenalRepository;
		this.relationshipTypeRepository=relationshipTypeRepository;
		this.visitorRepository =visitorRepository;
		this.visitorRepository =visitorRepository;
		this.prisonerRepository = prisonerRepository;
	}

 
	@GetMapping(path = APP_ROOT)
	 public String  racine( ) {
	 
          return "Hello";
	}
	
	
	
	@GetMapping(path = APP_ROOT + "visitors/searchByAmen/{gouvernorat}/{prison}/{codeResidance}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VisitorDto> searchByAmen(@PathVariable("gouvernorat") String gouvernorat,@PathVariable("prison") String prison,
			                                                           @PathVariable("codeResidance") String codeResidance) {
	String parameter=	gouvernorat+prison+codeResidance;
	 
	 try {
	        // Appeler la méthode Mono et attendre la réponse (bloquer l'exécution)
	        Mono<ApiResponseAmenVisitorDto> monoApiResponse = visitorService.callAmenVisitorAPI(parameter.trim());
	        ApiResponseAmenVisitorDto apiResponse = monoApiResponse.block();

	        if (apiResponse != null && apiResponse.getResult() != null) {
	            return apiResponse.getResult().stream()
	                .map(VisitorDto::fromApiResultAmenDto)
	                .collect(Collectors.toList());
	        } else {
	            // Gérer le cas où la réponse de l'API est nulle ou la liste de résultats est nulle
	            return Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
	        }
	    } catch (Exception e) {
	        // Gérer les exceptions ici
	        e.printStackTrace(); // Vous pouvez ajouter une meilleure gestion des exceptions
	        return Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
	    }

	}
	
	@GetMapping(path = APP_ROOT + "visit/findVisitByPrisonerId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RelationshipTypeAmenDto> findVisitByPrisonerId(@PathVariable("id") String id) {
	    try {
	        // Appeler la méthode du service
	        List<RelationshipTypeAmenDto> response = relationshipTypeService.findVisitByPrisonerId(id);

	        if (response != null) {
	            return response;
	        } else {
	            // Gérer le cas où la réponse du service est nulle
	            return Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
	        }
	    } catch (Exception e) {
	        // Gérer les exceptions ici
	        e.printStackTrace(); // Vous pouvez ajouter une meilleure gestion des exceptions
	        return Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
	    }
	}

	
	
 
	
//	@GetMapping(path = APP_ROOT + "visitors/amen", produces = MediaType.APPLICATION_JSON_VALUE)
//	public List<RelationshipType> amen( ) {
//		
//		List<VisiteursTime> listAmen =visiteursTimeRepository.findAll();
//    	List<RelationshipType> listVisit = new ArrayList<RelationshipType>();
//    	RelationshipType rt = null;
//    	for(VisiteursTime vt : listAmen) {
//    		
//    		Optional<PrisonerDto> p = prisonerPenalRepository.findById(
//    				vt.getNUMIDE().trim());
//    		 Visitor visitor =null ;
//    		 Prisoner prisoner =null ;
//    	if( p.isPresent()) {
//    		
//   		  
//    		   visitor= visitorRepository.save(new Visitor(null, vt.getIDENTITEVISITEUR() , null, null, null, vt.getNUMTELEPHONE()));
//    		  
//    		   prisoner = prisonerRepository.save(PrisonerDto.toEntity(p.get()));
//    		   
//    		   
//   		  rt = new RelationshipType(null, 
//   				prisoner , 
//   			                           	visitor,
//   			                           	vt.getRELATION(), 
//   			                           	getDayInArabic(vt.getNAME_JOUR_VISITE()), 
//   			                           	vt.getTIMEVISITE(), 
//   			                           	p.get().getCodeGouvernorat(), 
//   			                           	p.get().getCodePrison(), 
//   			                           	p.get().getNamePrison(), 
//							    				p.get().getCodeResidance(), 
//							    				p.get().getAnneeResidance(), 
//							    				"O", 
//							    				p.get().getNumDetention(),
//							    				"Visit", 
//							    				null, 
//							    				null);
//   		
//   		System.err.println(vt.getNUMIDE()+" success");
//   		listVisit.add(rt);
//    	}
//    	else {
//    		System.err.println(vt.getNUMIDE()+" failed");
//    	}
//    	}
//    	
//    	
//		
//		
//		
//	 
// 
//        return relationshipTypeRepository.saveAll(listVisit);  // Ou renvoyez une liste vide, ou une autre action appropriée
//   
//	}

    public static String getDayInArabic(String frenchDay) {
        String[] days = {
            "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة"
        };

        String[] frenchDays = {
            "LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI"
        };

        for (int i = 0; i < frenchDays.length; i++) {
            if (frenchDays[i].equalsIgnoreCase(frenchDay)) {
                return days[i];
            }
        }

        // Si le jour en français n'est pas trouvé, retourner une chaîne vide ou une erreur
        return "";
    }
}
