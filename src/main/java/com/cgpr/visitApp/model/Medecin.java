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
@Table(name = "medecin")
@SequenceGenerator(name = "SEQUENCE_MEDECIN", sequenceName = "SEQUENCE_MEDECIN", allocationSize = 1)
public class Medecin {

	 @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_MEDECIN")
	    private Long id;
	
	private String fullName; // الطبيب (د. أحمد علي...)
}
