//package com.cgpr.visitApp.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Entity
//@Table(name = "chambre")
//@SequenceGenerator(name = "SEQUENCE_CHAMBRE", sequenceName = "SEQUENCE_CHAMBRE", allocationSize = 1)
//public class Chambre {
//   
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CHAMBRE")
//    private Long id;
//    private String nom;
//    private String type;
//    
//    private String codeGouvernorat;
//  	private String  codePrison;
//  	private String  namePrison ;
//}