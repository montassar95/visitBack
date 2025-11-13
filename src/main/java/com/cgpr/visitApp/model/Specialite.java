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
@Table(name = "specialite")
@SequenceGenerator(name = "SEQUENCE_SPECIALITE", sequenceName = "SEQUENCE_SPECIALITE", allocationSize = 1)
public class Specialite {

	
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SPECIALITE")
   private Long id;


  private String name;  
}
