package com.cgpr.visitApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "visiteursTime2")
public class VisiteursTime {
	
	@Id
	private String id;
	
	private String NUMIDE;	
	
	private String IDENTITEVISITEUR	;
	
	private String RELATION	;
	
	private String NAME_JOUR_VISITE	;
	
	private String TIMEVISITE	;
	
	private String NUMTELEPHONE	;

}
