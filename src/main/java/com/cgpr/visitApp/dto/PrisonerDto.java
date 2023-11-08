package com.cgpr.visitApp.dto;

import javax.persistence.Entity;

import com.cgpr.visitApp.model.Prisoner;

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
 
public class PrisonerDto {

	private String idPrisoner;
	private String firstName;
	private String  lastName ;
	private String fatherName;
	private String  grandFatherName;
	private String codeNationalite;
	
	
	private String codeGouvernorat;
	private String codePrison;
	private String  namePrison ;
	private String  codeResidance;
	private String  anneeResidance;
	private String  statutResidance;
	private String  numDetention;
	private String eventDate;
	
	private String  etat;
	private String  type;
		
//	private String minAdlt;
// 	private String affairType;

	public static PrisonerDto fromEntity(Prisoner prisoner) {
		if (prisoner == null) {
			// to do throw an exception
			return null;
		}

		 

		return PrisonerDto.builder()
				.idPrisoner(prisoner.getIdPrisoner())
				.firstName(prisoner.getFirstName())
				.lastName(prisoner.getLastName())
				.fatherName(prisoner.getFatherName())
				.grandFatherName(prisoner.getGrandFatherName())
				.codeNationalite (prisoner.getCodeNationalite()) .build();
	}

	public static Prisoner toEntity(PrisonerDto prisonerDto) {
		if (prisonerDto == null) {
			// to do throw an exception
			return null;
		}

		return Prisoner.builder()
				.idPrisoner(prisonerDto.getIdPrisoner())
				.firstName(prisonerDto.getFirstName())
				.lastName(prisonerDto.getLastName())
				.fatherName(prisonerDto.getFatherName())
				.grandFatherName(prisonerDto.getGrandFatherName())
				.codeNationalite (prisonerDto.getCodeNationalite()) .build();
	}

	       
}
