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
@Table(name = "raison_sanction")
@SequenceGenerator(name = "SEQUENCE_RAISON_SANCTION", sequenceName = "SEQUENCE_RAISON_SANCTION", allocationSize = 1)
public class RaisonSanction {
   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_RAISON_SANCTION")
    private Long id;

    private String label;
}