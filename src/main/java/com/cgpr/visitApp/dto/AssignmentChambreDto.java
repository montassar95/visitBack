package com.cgpr.visitApp.dto;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cgpr.visitApp.model.AssignmentChambre;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.gestionChombre.Centre;
import com.cgpr.visitApp.model.gestionChombre.Chambre;
import com.cgpr.visitApp.model.gestionChombre.Complexe;
import com.cgpr.visitApp.model.gestionChombre.Pavillon;
import com.cgpr.visitApp.model.gestionChombre.PrisonerCategory;
import com.cgpr.visitApp.model.gestionChombre.RaisonAffectationChambre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentChambreDto {

	   private Long id;
	   private String prisonerId;

	    private String firstName;
	    private String fatherName;
	    private String grandFatherName;
	    private String lastName;
	    private String codeNationalite;

	   
	    private String codeGouvernorat;
	    private String codePrison;
	    private String namePrison;
	    private String codeResidance;
	    private String anneeResidance;
	    private LocalDate dateDebutResidance;

	    private Long chambreId;
	    private String chambreName; // <-- nom de la chambre pour affichage
	    
	    private Long prisonId;
	    private String prisonName;
	    // Nouvelle hiérarchie
	    private Long centreId;
	    private String centreName;
	    private Long complexId;
	    private String complexeName;
	    private Long pavillonId;
	    private String pavillonName;
	    
	    
	    
	    private Long prisonerCategoryId;
	   
	    private String prisonerCategoryLabel; // <-- label catégorie
	  
 	    private Long raisonAffectationId;
 	    private String raisonAffectationLabel; // <-- label raison
	
	    private Long raisonFermerId;
	    private String raisonFermerLabel; // <-- label raison
	   
	    
	    
	    
	    
	    
	    private LocalDate startDate;
	    private LocalDate endDate;

	    // Entity → DTO
	    public static AssignmentChambreDto fromEntity(AssignmentChambre entity) {
	        if (entity == null) return null;
	        Prisoner p = entity.getPrisoner();
	        return AssignmentChambreDto.builder()
	        		.id(entity.getId())
	                .prisonerId(p != null ? p.getIdPrisoner() : null)
	                .firstName(p != null ? p.getFirstName() : null)
	                .fatherName(p != null ? p.getFatherName() : null)
	                .grandFatherName(p != null ? p.getGrandFatherName() : null)
	                .lastName(p != null ? p.getLastName() : null)
	                .codeNationalite(p != null ? p.getCodeNationalite() : null)
	                
	                .codeGouvernorat(entity.getCodeGouvernorat())
	                .codePrison(entity.getCodePrison())
	                .namePrison( entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getName() != null ?  entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getName()  : null)
	                .codeResidance(entity.getCodeResidance())
	                .anneeResidance(entity.getAnneeResidance())
	                .dateDebutResidance(null)
	                
	                
	                .prisonId(entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getId() != null ? entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getId() : null)
	                 .prisonName(entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getId() != null ? entity.getChambre().getPavillon().getComplexe().getCentre().getPrison().getName() : null)
	                // Centre / Complexe / Pavillon
	               
	                .centreId(entity.getChambre().getPavillon().getComplexe().getCentre().getId() != null ? entity.getChambre().getPavillon().getComplexe().getCentre().getId() : null)
	                .centreName( entity.getChambre().getPavillon().getComplexe().getCentre().getName() != null ?  entity.getChambre().getPavillon().getComplexe().getCentre().getName()  : null)
	                
	                .complexId(entity.getChambre().getPavillon().getComplexe().getId()  != null ?entity.getChambre().getPavillon().getComplexe().getId() : null)
	                .complexeName(entity.getChambre().getPavillon().getComplexe().getName() != null ? entity.getChambre().getPavillon().getComplexe().getName() : null)
	               
	                .pavillonId(entity.getChambre().getPavillon().getId() != null ? entity.getChambre().getPavillon().getId()  : null)
	                .pavillonName(entity.getChambre().getPavillon().getName() != null ? entity.getChambre().getPavillon().getName() : null)

	                
	                
	                .chambreId(entity.getChambre() != null ? entity.getChambre().getId() : null)
	                .chambreName(entity.getChambre() != null ? entity.getChambre().getName() : null)

	                .prisonerCategoryId(entity.getPrisonerCategory() != null ? entity.getPrisonerCategory().getId() : null)
	                .prisonerCategoryLabel(entity.getPrisonerCategory() != null ? entity.getPrisonerCategory().getLabel() : null)

	                .raisonAffectationId(entity.getRaisonAffectationChambre() != null ? entity.getRaisonAffectationChambre().getId() : null)
	                .raisonAffectationLabel(entity.getRaisonAffectationChambre() != null ? entity.getRaisonAffectationChambre().getLibelle() : null)

	                .raisonFermerId(entity.getRaisonFermerChambre() != null ? entity.getRaisonFermerChambre().getId() : null)
	                .raisonFermerLabel(entity.getRaisonFermerChambre() != null ? entity.getRaisonFermerChambre().getLibelle() : null)

	                
	                
	                .startDate(entity.getStartDate())
	                .endDate(entity.getEndDate())
	                .build();
	    }

	 // DTO → Entity
	 // DTO → Entity
	    public static AssignmentChambre toEntity(AssignmentChambreDto dto) {
	        if (dto == null) return null;

	        AssignmentChambre entity = new AssignmentChambre();
	        entity.setId(dto.getId());

	        // Prisoner : on suppose que le service/controller va injecter l'objet Prisoner
	        if (dto.getPrisonerId() != null) {
	            Prisoner prisoner = new Prisoner();
	            prisoner.setIdPrisoner(dto.getPrisonerId());
	            entity.setPrisoner(prisoner);
	        }

	        // Chambre et sa hiérarchie (Pavillon → Complexe → Centre)
	        if (dto.getChambreId() != null) {
	            Chambre chambre = new Chambre();
	            chambre.setId(dto.getChambreId());
	            chambre.setName(dto.getChambreName());

	            if (dto.getPavillonId() != null) {
	                Pavillon pavillon = new Pavillon();
	                pavillon.setId(dto.getPavillonId());
	                pavillon.setName(dto.getPavillonName());

	                if (dto.getComplexId() != null) {
	                    Complexe complexe = new Complexe();
	                    complexe.setId(dto.getComplexId());
	                    complexe.setName(dto.getComplexeName());
	                    pavillon.setComplexe(complexe);

	                    if (dto.getCentreId() != null) {
	                        Centre centre = new Centre();
	                        centre.setId(dto.getCentreId());
	                        centre.setName(dto.getCentreName());
	                        complexe.setCentre(centre);
	                    }
	                }

	                chambre.setPavillon(pavillon);
	            }

	            entity.setChambre(chambre);
	        }

	        // PrisonerCategory
	        if (dto.getPrisonerCategoryId() != null) {
	            PrisonerCategory category = new PrisonerCategory();
	            category.setId(dto.getPrisonerCategoryId());
	            category.setLabel(dto.getPrisonerCategoryLabel());
	            entity.setPrisonerCategory(category);
	        }

	        // RaisonAffectationChambre
	        if (dto.getRaisonAffectationId() != null) {
	            RaisonAffectationChambre raison = new RaisonAffectationChambre();
	            raison.setId(dto.getRaisonAffectationId());
	            raison.setLibelle(dto.getRaisonAffectationLabel());
	            entity.setRaisonAffectationChambre(raison);
	        }
	        
	     // RaisonFermerChambre
	        if (dto.getRaisonFermerId() != null) {
	            RaisonAffectationChambre raison = new RaisonAffectationChambre();
	            raison.setId(dto.getRaisonFermerId());
	            raison.setLibelle(dto.getRaisonFermerLabel());
	            entity.setRaisonFermerChambre(raison);
	        }
	        

	        // Infos prison / résidence
	        entity.setCodeGouvernorat(dto.getCodeGouvernorat());
	        entity.setCodePrison(dto.getCodePrison());
	        entity.setNamePrison(dto.getNamePrison());
	        entity.setCodeResidance(dto.getCodeResidance());
	        entity.setAnneeResidance(dto.getAnneeResidance());

	        // Dates
	        entity.setStartDate(dto.getStartDate());
	        entity.setEndDate(dto.getEndDate());

	        return entity;
	    }


}
