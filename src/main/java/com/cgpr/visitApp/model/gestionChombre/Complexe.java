package com.cgpr.visitApp.model.gestionChombre;

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
@Table(name = "complexe")
@SequenceGenerator(name = "SEQUENCE_COMPLEXE", sequenceName = "SEQUENCE_COMPLEXE", allocationSize = 1)
public class Complexe {

	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_COMPLEXE")
    private Long id;
	
	
	private String name;
	
	
	@ManyToOne
	@JoinColumn(name = "centre_id")
	private Centre centre;
}
