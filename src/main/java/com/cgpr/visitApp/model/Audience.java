package com.cgpr.visitApp.model;

 
 
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "audience")
@SequenceGenerator(name = "SEQUENCE_AUDIENCE", sequenceName = "SEQUENCE_AUDIENCE", allocationSize = 1)
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_AUDIENCE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prisoner_id", referencedColumnName = "idPrisoner")
    private Prisoner prisoner;

    
    
    private String codeGouvernorat;
  	private String  codePrison;
  	private String  namePrison ;
  	private String  codeResidance;
  	private String  anneeResidance;
  	
  	
    private LocalDate dateAudience;

//    private String tribunal; // nom du tribunal
//   
//    private String typeAudience; // optionnel: type de session
    
    
    @ManyToOne
    @JoinColumn(name = "type_audience_id")
    private TypeAudience typeAudience;

    @ManyToOne
    @JoinColumn(name = "tribunal_id")
    private Tribunal tribunal;

	public Audience(Prisoner prisoner, String codeGouvernorat, String codePrison, String namePrison,
			String codeResidance, String anneeResidance, LocalDate dateAudience, TypeAudience typeAudience,
			Tribunal tribunal) {
		super();
		this.prisoner = prisoner;
		this.codeGouvernorat = codeGouvernorat;
		this.codePrison = codePrison;
		this.namePrison = namePrison;
		this.codeResidance = codeResidance;
		this.anneeResidance = anneeResidance;
		this.dateAudience = dateAudience;
		this.typeAudience = typeAudience;
		this.tribunal = tribunal;
	}

 

 
    
    
    
}
