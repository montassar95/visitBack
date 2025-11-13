package com.cgpr.visitApp.model;

 

import javax.persistence.*;
import lombok.*;

 
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "rapporteur")
@SequenceGenerator(name = "SEQUENCE_RAPPORTEUR", sequenceName = "SEQUENCE_RAPPORTEUR", allocationSize = 1)
public class Rapporteur {

    
    
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_RAPPORTEUR")
    private Long id;
    
    
    
    @Column(name = "numero_administartif")
    private String numeroAdministartif;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;
    
    
    
    @Column(name = "code_gouvernorat")
    private String codeGouvernorat;

    @Column(name = "code_prison")
    private String codePrison;
    

    @Column(name = "name_prison")
    private String namePrison;
}


