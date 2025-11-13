package com.cgpr.visitApp.model;

 

import javax.persistence.*;
import lombok.*;
 

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "detenu_participant")
@SequenceGenerator(name = "SEQUENCE_PARTICIPANT", sequenceName = "SEQUENCE_PARTICIPANT", allocationSize = 1)
public class DetenuParticipant {

 
	// Informations sur le prisonnier participant
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_PARTICIPANT")
	private Long id;
	
    @Column(name = "id_prisoner")
    private String idPrisoner;

    @Column(name = "code_gouvernorat")
    private String codeGouvernorat;

    @Column(name = "code_prison")
    private String codePrison;
    

    @Column(name = "name_prison")
    private String namePrison;
    
    @Column(name = "code_residance")
    private String codeResidance;

    @Column(name = "annee_residance")
    private String anneeResidance;
    
    
    


 
 
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;
}

