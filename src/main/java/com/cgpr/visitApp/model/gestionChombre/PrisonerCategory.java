package com.cgpr.visitApp.model.gestionChombre;

import javax.persistence.Column;
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
@Table(name = "PRISONER_CATEGORY")
@SequenceGenerator(name = "SEQUENCE_PRISONER_CATEGORY", sequenceName = "SEQUENCE_PRISONER_CATEGORY", allocationSize = 1)
 
public class PrisonerCategory {
 
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_PRISONER_CATEGORY")
    private Long id;
    
    
    @Column(name = "LABEL", nullable = false)
    private String label;

    // getters et setters
}