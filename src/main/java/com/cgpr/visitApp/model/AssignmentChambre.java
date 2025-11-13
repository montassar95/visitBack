package com.cgpr.visitApp.model;

import java.time.LocalDate;

import javax.persistence.*;

import com.cgpr.visitApp.model.gestionChombre.Chambre;
import com.cgpr.visitApp.model.gestionChombre.PrisonerCategory;
import com.cgpr.visitApp.model.gestionChombre.RaisonAffectationChambre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ASSIGNMENT_CHAMBRE")
@SequenceGenerator(name = "SEQUENCE_ASS_CHAMBRE", sequenceName = "SEQUENCE_ASS_CHAMBRE", allocationSize = 1)
public class AssignmentChambre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_ASS_CHAMBRE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prisoner_id", referencedColumnName = "idPrisoner")
    private Prisoner prisoner;

 
    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance; 
    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;

    @ManyToOne
    @JoinColumn(name = "prisoner_cat_id")
    private PrisonerCategory prisonerCategory;

    @ManyToOne
    @JoinColumn(name = "raison_aff_chambre")
    private RaisonAffectationChambre  raisonAffectationChambre;

    @ManyToOne
    @JoinColumn(name = "raison_fermer_chambre")
    private RaisonAffectationChambre  raisonFermerChambre;
    
    
    
    private LocalDate startDate; // Date d’entrée
    private LocalDate endDate; // Date de sortie
	public AssignmentChambre(Prisoner prisoner, String codeGouvernorat, String codePrison, String namePrison,
			String codeResidance, String anneeResidance, Chambre chambre, PrisonerCategory prisonerCategory,
			RaisonAffectationChambre raisonAffectationChambre, RaisonAffectationChambre raisonFermerChambre,
			LocalDate startDate, LocalDate endDate) {
		super();
		this.prisoner = prisoner;
		this.codeGouvernorat = codeGouvernorat;
		this.codePrison = codePrison;
		this.namePrison = namePrison;
		this.codeResidance = codeResidance;
		this.anneeResidance = anneeResidance;
		this.chambre = chambre;
		this.prisonerCategory = prisonerCategory;
		this.raisonAffectationChambre = raisonAffectationChambre;
		this.raisonFermerChambre = raisonFermerChambre;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	 
    
    
 
    
    
    
    
    
    
 
   
}
