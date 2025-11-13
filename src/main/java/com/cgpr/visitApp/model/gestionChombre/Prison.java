package com.cgpr.visitApp.model.gestionChombre;

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
@Table(name = "prison")
@SequenceGenerator(name = "SEQUENCE_PRISON", sequenceName = "SEQUENCE_PRISON", allocationSize = 1)
public class Prison {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_PRISON")
    private Long id;
	
	
	private String codeGou;
	private String codePr;
	private String name;
	private String  type;

}
