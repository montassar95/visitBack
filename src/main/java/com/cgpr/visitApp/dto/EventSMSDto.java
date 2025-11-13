package com.cgpr.visitApp.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class EventSMSDto {
	
	 private String prisonerId;
	    private String prisonerFirstName;
	    private String prisonerLastName;
	    private String relationShip;
	    private String namePrison;
	    private String numDetention;
	    private String codeResidance;
	    private String anneeResidance;
	    private String libelleStatutResidance;
	    private String eventDate;
	    private String statutSMS;
	    private Date sentDate;
	    private String visitorFirstName;
	    private String visitorLastName;
	    private String visitorPhone;
	    private String statutDLR;
	    private Date dlrDate;
	    

	    // Constructeur requis par JPQL
	    public EventSMSDto(String prisonerId, String prisonerFirstName, String prisonerLastName,
	                       String relationShip, String namePrison, String numDetention,
	                       String codeResidance, String anneeResidance, String libelleStatutResidance,
	                       String eventDate, String statutSMS, Date sentDate,
	                       String visitorFirstName, String visitorLastName, String visitorPhone, String statutDLR,  Date dlrDate) {
	        this.prisonerId = prisonerId;
	        this.prisonerFirstName = prisonerFirstName;
	        this.prisonerLastName = prisonerLastName;
	        this.relationShip = relationShip;
	        this.namePrison = namePrison;
	        this.numDetention = numDetention;
	        this.codeResidance = codeResidance;
	        this.anneeResidance = anneeResidance;
	        this.libelleStatutResidance = libelleStatutResidance;
	        this.eventDate = eventDate;
	        this.statutSMS = statutSMS;
	        this.sentDate = sentDate;
	        this.visitorFirstName = visitorFirstName;
	        this.visitorLastName = visitorLastName;
	        this.visitorPhone = visitorPhone;
	       this.statutDLR= statutDLR;
	       this.dlrDate = dlrDate;
	    }

}
