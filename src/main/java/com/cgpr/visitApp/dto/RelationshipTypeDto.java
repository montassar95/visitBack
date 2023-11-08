package com.cgpr.visitApp.dto;

import java.util.ArrayList;
import java.util.List;

import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationshipTypeDto {

    private String idPrisoner;
   // private String minAdlt;
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String lastName;
    
	private String codeNationalite;
	private String  etat;
	private String  type;
//    private String affairType;
    
    private String codeGouvernorat;
	private String codePrison;
	private String  namePrison ;
	private String  codeResidance;
	private String  anneeResidance;
	private String  statutResidance;
	private String  numDetention;
	
	
	
        private String day;
	    private String time;
	    private String  statutSMS;
	 
		private String eventDate;
        private List<VisitorDto> visitorsDto;
    
         private String room;
    
         private String affaires;
    

    public static List<RelationshipType> toListEntity(RelationshipTypeDto relationshipTypeDto) {
        if (relationshipTypeDto == null) {
            throw new IllegalArgumentException("relationshipTypeDto ne peut pas être null");
        }
     
        List<RelationshipType> relationshipTypes = new ArrayList<>();

        Prisoner prisoner = Prisoner.builder()
                .idPrisoner(relationshipTypeDto.getIdPrisoner())
                .firstName(relationshipTypeDto.getFirstName())
                .lastName(relationshipTypeDto.getLastName())
                .fatherName(relationshipTypeDto.getFatherName())
                .grandFatherName(relationshipTypeDto.getGrandFatherName())
                .codeNationalite(relationshipTypeDto.getCodeNationalite())
//                .minAdlt(relationshipTypeDto.getMinAdlt())
                .build();

        List<VisitorDto> visitorsDto = relationshipTypeDto.getVisitorsDto();

        for (VisitorDto visitorDto : visitorsDto) {
            RelationshipType relationshipType = RelationshipType.builder()
                    .prisoner(prisoner)
                    .visitor(VisitorDto.toEntity(visitorDto))
                    .relationShip(visitorDto.getRelationship())
                    .day(relationshipTypeDto.getDay())
                    .time(relationshipTypeDto.getTime())
                    .statutSMS(relationshipTypeDto.getStatutSMS())
                    .eventDate(relationshipTypeDto.getEventDate())
                    .codeGouvernorat(relationshipTypeDto.getCodeGouvernorat())  
        	        .codePrison(relationshipTypeDto.getCodePrison())
        			 .namePrison (relationshipTypeDto.getNamePrison())
        			 .codeResidance(relationshipTypeDto.getCodeResidance())
        			.anneeResidance(relationshipTypeDto.getAnneeResidance())
        			 .statutResidance(relationshipTypeDto.getStatutResidance())
        			 .numDetention(relationshipTypeDto.getNumDetention())
                    .build();
            relationshipTypes.add(relationshipType);
        }

        return relationshipTypes;
    }
    
    public static RelationshipTypeDto fromListEntity(List<RelationshipType> list) {
        if (list == null || list.isEmpty()) {
            // Si la liste est nulle ou vide, nous affichons un message d'erreur
            System.err.println("La liste ne peut pas être null ou vide");
            return null;
        }

        // Nous allons utiliser le premier élément de la liste pour extraire les informations sur le prisonnier
        RelationshipType firstRelationshipType = list.get(0);
        Prisoner prisoner = firstRelationshipType.getPrisoner();

        // Création de l'objet RelationshipTypeDto avec les informations du prisonnier
        RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.builder()
                .idPrisoner(prisoner.getIdPrisoner())
                .firstName(prisoner.getFirstName())
                .fatherName(prisoner.getFatherName())
                .grandFatherName(prisoner.getGrandFatherName())
                .lastName(prisoner.getLastName())
                .codeNationalite(prisoner.getCodeNationalite())
         	   
                .day(firstRelationshipType.getDay())
                .time(firstRelationshipType.getTime())
                .statutSMS(firstRelationshipType.getStatutSMS())
                .eventDate(firstRelationshipType.getEventDate())
                .codeGouvernorat(firstRelationshipType.getCodeGouvernorat())  
                .codePrison(firstRelationshipType.getCodePrison())
                .namePrison(firstRelationshipType.getNamePrison())
                .codeResidance(firstRelationshipType.getCodeResidance())
                .anneeResidance(firstRelationshipType.getAnneeResidance())
                .statutResidance(firstRelationshipType.getStatutResidance())
                .numDetention(firstRelationshipType.getNumDetention())
                .build();

        List<VisitorDto> visitorsDto = new ArrayList<>();

        for (RelationshipType relationshipType : list) {
            // Vérification pour éviter les doublons de visiteurs
            if (!visitorAlreadyExists(visitorsDto, relationshipType.getVisitor())) {
                VisitorDto visitorDto = VisitorDto.fromEntityRelationshipType(relationshipType);
                visitorsDto.add(visitorDto);
            }
        }

        relationshipTypeDto.setVisitorsDto(visitorsDto);

        return relationshipTypeDto;
    }

    // Fonction pour vérifier si un visiteur existe déjà dans la liste
    private static boolean visitorAlreadyExists(List<VisitorDto> visitorsDto, Visitor visitor) {
        for (VisitorDto existingVisitor : visitorsDto) {
            if (existingVisitor.getIdVisitor().equals(visitor.getIdVisitor())) {
                return true;
            }
        }
        return false;
    }

//    public static RelationshipTypeDto convertToRelationshipTypeDto(PrisonerDto prisonerDto) {
//    	RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.builder()
//    		    .idPrisoner(prisonerDto.getIdPrisoner())
////    		    .minAdlt(prisoner.getMinAdlt())
//    		    .firstName(prisonerDto.getFirstName())
//    		    .fatherName(prisonerDto.getFatherName())
//    		    .grandFatherName(prisonerDto.getGrandFatherName())
//    		    .lastName(prisonerDto.getLastName())
//    		    .visitorsDto(new ArrayList<VisitorDto>())
//    		    .codeGouvernorat(prisonerDto.getCodeGouvernorat())  
//    	        .codePrison(prisonerDto.getCodePrison())
//    			 .namePrison (prisonerDto.getNamePrison())
//    			 .codeResidance(prisonerDto.getCodeResidance())
//    			.anneeResidance(prisonerDto.getAnneeResidance())
//    			 .statutResidance(prisonerDto.getStatutResidance())
//    		    
//    		    .build();
//        return relationshipTypeDto;
//    }
	public static RelationshipTypeDto convertPrisonerDtoToRelationshipTypeDto(PrisonerDto prisonerDto,List<VisitorDto> listVisitors) {
    	RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.builder()
    		    .idPrisoner(prisonerDto.getIdPrisoner())
//    		    .minAdlt(prisoner.getMinAdlt())
    		    .firstName(prisonerDto.getFirstName())
    		    .fatherName(prisonerDto.getFatherName())
    		    .grandFatherName(prisonerDto.getGrandFatherName())
    		    .lastName(prisonerDto.getLastName())
    		    .codeNationalite(prisonerDto.getCodeNationalite())
    		    .etat(prisonerDto.getEtat())
    		    .visitorsDto(listVisitors)
    		     .eventDate(prisonerDto.getEventDate())
    		    .codeGouvernorat(prisonerDto.getCodeGouvernorat())  
    	        .codePrison(prisonerDto.getCodePrison())
    			 .namePrison (prisonerDto.getNamePrison())
    			 .codeResidance(prisonerDto.getCodeResidance())
    			.anneeResidance(prisonerDto.getAnneeResidance())
    			 .statutResidance(prisonerDto.getStatutResidance())
    			 .numDetention(prisonerDto.getNumDetention())
    		    
    		  
    		    .build();
        return relationshipTypeDto;
	}
	
	
	public static List<RelationshipTypeAmenDto> fromListEntityAmen(List<RelationshipType> list) {
	    List<RelationshipTypeAmenDto> responseAmen = new ArrayList<RelationshipTypeAmenDto>();

	    try {
	        if (list == null || list.isEmpty()) {
	            // Si la liste est nulle ou vide, nous affichons un message d'erreur
	            System.err.println("La liste ne peut pas être null ou vide");
	            return responseAmen;
	        }

	        for (RelationshipType relationshipType : list) {
	            RelationshipTypeAmenDto rAmenDto = RelationshipTypeAmenDto.builder()
	                .numide(relationshipType.getPrisoner().getIdPrisoner())
	                .day(relationshipType.getDay())
	                .time(relationshipType.getTime())
	                .visiteur(relationshipType.getVisitor().getFirstName())
	                .phone(relationshipType.getVisitor().getPhone())
	                .relationship(relationshipType.getRelationShip())
	                .statutSMS(relationshipType.getStatutSMS())
	                .build();

	            responseAmen.add(rAmenDto);
	        }
	    } catch (Exception e) {
	        // En cas d'exception, afficher un message d'erreur
	        System.err.println("Une exception s'est produite : " + e.getMessage());
	        // Retourner une liste vide
	        return new ArrayList<RelationshipTypeAmenDto>();
	    }

	    return responseAmen;
	}

	@Override
	public String toString() {
		return "RelationshipTypeDto [idPrisoner=" + idPrisoner + ", firstName=" + firstName + ", fatherName="
				+ fatherName + ", grandFatherName=" + grandFatherName + ", lastName=" + lastName + ", codeGouvernorat="
				+ codeGouvernorat + ", codePrison=" + codePrison + ", namePrison=" + namePrison + ", codeResidance="
				+ codeResidance + ", anneeResidance=" + anneeResidance + ", statutResidance=" + statutResidance
				+ ", visitorsDto=" + visitorsDto + "]";
	}
}

//if (relationshipType.getVisitor().getLastName() != null) {
//    builder.lastName(relationshipType.getVisitor().getLastName());
//} else {
//    builder.lastName(""); // Champ vide si lastName est nul
//}
//
//if (relationshipType.getVisitor().getFatherName() != null) {
//    builder.fatherName(relationshipType.getVisitor().getFatherName());
//} else {
//    builder.fatherName(""); // Champ vide si fatherName est nul
//}
//
//if (relationshipType.getVisitor().getGrandFatherName() != null) {
//    builder.grandFatherName(relationshipType.getVisitor().getGrandFatherName());
//} else {
//    builder.grandFatherName(""); // Champ vide si grandFatherName est nul
//}
