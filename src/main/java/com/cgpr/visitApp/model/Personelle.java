package com.cgpr.visitApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "personelle")
public class Personelle  implements Serializable {
	@Id
	private long id;
	 
	private String nom;
	private String prenom;
	

	private String login;
	private String pwd;
	
	   private String codeGouvernorat;
		private String codePrison;
		private String type;
		private String  name ;
		private Integer idRole;
		private String  labelRole ;
		
}
