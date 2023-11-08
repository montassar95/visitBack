package com.cgpr.visitApp.model;

import java.util.Date;

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
@Table(name = "relationshipType")
@SequenceGenerator(name = "SEQUENCE_RELATIOSHIP", sequenceName = "SEQUENCE_RELATIOSHIP", allocationSize = 1)
public class RelationshipType{
	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_RELATIOSHIP")
    private Long id;
	@ManyToOne
    @JoinColumn(name = "prisoner_id")
    private Prisoner prisoner;
    
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    private String relationShip;
    
    private String day;
    private String time;
     private String codeGouvernorat;
	private String codePrison;
	private String  namePrison ;
	private String  codeResidance;
	private String  anneeResidance;
	private String  statutResidance;
	private String  numDetention;
	

	
	private String  libelleStatutResidance;
	
	private String  statutSMS;
	 
	private String eventDate;
	
	private Date sentDate;
	
	public RelationshipType(Prisoner prisoner, Visitor visitor, String relationShip, String day, String time,
			String codeGouvernorat, String codePrison, String namePrison, String codeResidance, String anneeResidance,
			String statutResidance, String numDetention, String libelleStatutResidance, String statutSMS,
			String eventDate) {
		super();
		this.prisoner = prisoner;
		this.visitor = visitor;
		this.relationShip = relationShip;
		this.day = day;
		this.time = time;
		this.codeGouvernorat = codeGouvernorat;
		this.codePrison = codePrison;
		this.namePrison = namePrison;
		this.codeResidance = codeResidance;
		this.anneeResidance = anneeResidance;
		this.statutResidance = statutResidance;
		this.numDetention = numDetention;
		this.libelleStatutResidance = libelleStatutResidance;
		this.statutSMS = statutSMS;
		this.eventDate = eventDate;
	}
	
	
	

}