package com.cgpr.visitApp.model;

import java.time.LocalDate;

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
@Table(name = "hospital")
@SequenceGenerator(name = "SEQUENCE_HOSPITAL", sequenceName = "SEQUENCE_HOSPITAL", allocationSize = 1)
public class Hospital {

	
	     @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_HOSPITAL")
	    private Long id;
	 
	 
	   private String name; // المستشفى (جامعي، عسكري...)

}
