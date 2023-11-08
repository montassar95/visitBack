package com.cgpr.visitApp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "prisoner")
public class Prisoner {
	@Id
    private String idPrisoner;
	private String firstName;
	private String  lastName ;
	private String fatherName;
	private String  grandFatherName;
	private String codeNationalite;
//	private String minAdlt;
//	private String affairType;
//	nationalite

 
}
