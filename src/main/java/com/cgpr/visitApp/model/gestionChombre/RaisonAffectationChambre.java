package com.cgpr.visitApp.model.gestionChombre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "RAISON_AFFECTATION_CHAMBRE")
@SequenceGenerator(name = "SEQUENCE_RAISON_AFF_CHAMBRE", sequenceName = "SEQUENCE_RAISON_AFF_CHAMBRE", allocationSize = 1)

public class RaisonAffectationChambre {

 
    
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_RAISON_AFF_CHAMBRE")
    private Long id;
	
	
    @Column(name = "LIBELLE", nullable = false)
    private String libelle;  // ex : "Promotion", "Évacuation", "Transfert médical"

    
    private String type;  //si F donc fermer si O donc normal 
    private String typeMouvement; 
    // getters et setters
}
