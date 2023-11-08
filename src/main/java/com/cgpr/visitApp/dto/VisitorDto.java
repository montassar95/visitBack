package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.dto.amenDto.ApiResultAmenVisitorDto;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitorDto {
	
	    private Long idVisitor;
	    private String firstName;
	    private String fatherName;
	    private String grandFatherName;
	    private String lastName;
	    private String phone;
	    private String relationship;
	  
	    
	    public static VisitorDto fromEntity(Visitor visitor) {
			if (visitor == null) {
				// to do throw an exception
				return null;
			}

			 

			return VisitorDto.builder().idVisitor(visitor.getIdVisitor()).firstName(visitor.getFirstName())
					.lastName(visitor.getLastName())
	                .fatherName(visitor.getFatherName())
	                .grandFatherName(visitor.getGrandFatherName())
	                .phone(visitor.getPhone()).build();
		}

	    public static VisitorDto fromEntityRelationshipType(RelationshipType relationshipType) {
	        if (relationshipType == null) {
	            // À faire : lancer une exception ou gérer ce cas d'erreur
	            return null;
	        }

	        System.err.println(relationshipType.toString());

	        VisitorDto.VisitorDtoBuilder builder = VisitorDto.builder()
	            .idVisitor(relationshipType.getVisitor().getIdVisitor())
	            .firstName(relationshipType.getVisitor().getFirstName())
	            .phone(relationshipType.getVisitor().getPhone())
	            .relationship(relationshipType.getRelationShip());

	        // Vérifier et traiter les champs lastName, fatherName et grandFatherName
	        if (relationshipType.getVisitor().getLastName() != null) {
	            builder.lastName(relationshipType.getVisitor().getLastName());
	        } else {
	            builder.lastName(""); // Champ vide si lastName est nul
	        }

	        if (relationshipType.getVisitor().getFatherName() != null) {
	            builder.fatherName(relationshipType.getVisitor().getFatherName());
	        } else {
	            builder.fatherName(""); // Champ vide si fatherName est nul
	        }

	        if (relationshipType.getVisitor().getGrandFatherName() != null) {
	            builder.grandFatherName(relationshipType.getVisitor().getGrandFatherName());
	        } else {
	            builder.grandFatherName(""); // Champ vide si grandFatherName est nul
	        }

	        return builder.build();
	    }

	    
	    
		public static Visitor toEntity(VisitorDto visitorDto) {
			if (visitorDto == null) {
				// to do throw an exception
				return null;
			}

			return Visitor.builder().idVisitor(visitorDto.getIdVisitor()).firstName(visitorDto.getFirstName()) 
					.lastName(visitorDto.getLastName())
	                .fatherName(visitorDto.getFatherName())
	                .grandFatherName(visitorDto.getGrandFatherName())
					.phone(visitorDto.getPhone()).build();
		}
		
		
	    public static VisitorDto fromApiResultAmenDto(ApiResultAmenVisitorDto apiResultAmenDto) {
				if (apiResultAmenDto == null) {
					// to do throw an exception
					return null;
				}

				 

				return VisitorDto.builder().idVisitor( Long.parseLong(  apiResultAmenDto.getTcode_VISITEUR_CIN())).firstName(apiResultAmenDto.getTnom_PRENOM_VISITEUR()).relationship(apiResultAmenDto.getTlibelle_TYPE_LIAISON())
						 .build();
			}
	    
}
