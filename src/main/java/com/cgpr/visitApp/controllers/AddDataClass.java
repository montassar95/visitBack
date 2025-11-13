package com.cgpr.visitApp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
//import com.cgpr.visitApp.model.VisiteursTime;
import com.cgpr.visitApp.model.Visitor;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.repository.RelationshipTypeRepository;
//import com.cgpr.visitApp.repository.VisiteursTimeRepository;


@Component
public class AddDataClass {
	
	
//	@Autowired
//	private   VisiteursTimeRepository visiteursTimeRepository;
	
	@Autowired
	private   PrisonerPenalRepository prisonerPenalRepository;
	
	@Autowired
	private   RelationshipTypeRepository relationshipTypeRepository;
	
//    public   void addData( ) {
// 
//    	 
//    	List<VisiteursTime> listAmen =visiteursTimeRepository.findAll();
//    	List<RelationshipType> listVisit = new ArrayList<RelationshipType>();
//    	RelationshipType rt = null;
//    	for(VisiteursTime vt : listAmen) {
//    		Optional<PrisonerDto> p = prisonerPenalRepository.findById(vt.getNUMIDE());
//    		  Visitor visitor = new Visitor(null, vt.getIDENTITEVISITEUR() , null, null, null, vt.getNUMTELEPHONE());
//    		  
//    		  
//    		  rt = new RelationshipType(null, 
//    				PrisonerDto.toEntity( p.get()) , 
//    			                           	visitor,
//    			                           	vt.getRELATION(), 
//    			                           	getDayInArabic(vt.getNAME_JOUR_VISITE()), 
//    			                           	vt.getTIMEVISITE(), 
//    			                           	p.get().getCodeGouvernorat(), 
//    			                           	p.get().getCodePrison(), 
//    			                           	p.get().getNamePrison(), 
//							    				p.get().getCodeResidance(), 
//							    				p.get().getAnneeResidance(), 
//							    				"O", 
//							    				p.get().getNumDetention(),
//							    				"Visit", 
//							    				null, 
//							    				null);
//    		
//    		
//    		listVisit.add(rt);
//    	}
//    	
//    	
//    	 relationshipTypeRepository.saveAll(listVisit);
//   
//    }
    
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
