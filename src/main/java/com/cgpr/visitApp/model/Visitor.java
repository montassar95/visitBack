package com.cgpr.visitApp.model;

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
@Table(name = "visitor")
 @SequenceGenerator(name = "SEQUENCE_VISITOR", sequenceName = "SEQUENCE_VISITOR", allocationSize = 1)
public class Visitor{
	      @Id
 	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_VISITOR")
 
	    private Long  idVisitor;
	    private String firstName;
	    private String fatherName;
	    private String grandFatherName;
	    private String lastName;
	    private String phone;
//	    private String relationshipType;
//	    private String day;
//	    private String time;
}


 