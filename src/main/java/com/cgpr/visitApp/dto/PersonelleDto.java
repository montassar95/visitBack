package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.Personelle;

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
public class PersonelleDto   {
 
//	private long id;
 
 
	private String nom;
	private String prenom;
	 
	private PrisonDto  prison;
	 
 

//	private String login;
//	private String pwd;
	
//	  private String codeGouvernorat;
//			private String codePrison;
//			private String  name ;
//			private Integer idRole;
	
	private  RoleDto role;
	
	public static PersonelleDto fromEntity(Personelle personelle) {
        if (personelle == null) {
            return null;
        }

        return PersonelleDto.builder()
//                .id(personelle.getId())
                
                .nom(personelle.getNom())
                .prenom(personelle.getPrenom())
//                .idRole(personelle.getIdRole())
//                .login(personelle.getLogin())
//                .pwd(personelle.getPwd())
                
//                .prison.setCodeGouvernorat(personelle.getCodeGouvernorat())
//                .prison.setCodePrison(personelle.getCodePrison())
//                .prison  .setName(personelle.getName())
                .prison( new PrisonDto(personelle.getCodeGouvernorat(), personelle.getCodePrison(), personelle.getName()) )
                .role(new RoleDto(personelle.getIdRole(),personelle.getLabelRole()))
                .build();
    }

    public static Personelle toEntity(PersonelleDto personelleDto) {
        if (personelleDto == null) {
            return null;
        }

        return Personelle.builder()
//                .id(personelleDto.getId())
                
                .nom(personelleDto.getNom())
                .prenom(personelleDto.getPrenom())
//                .idRole(personelleDto.getIdRole())
//                .login(personelleDto.getLogin())
//                .pwd(personelleDto.getPwd())
                
//                .codeGouvernorat(personelleDto.getCodeGouvernorat())
//                . codePrison(personelleDto.getCodePrison())
//                . name(personelleDto.getName()) 
                .build();
    }

}
