package com.cgpr.visitApp.dto;
 

import com.cgpr.visitApp.model.gestionChombre.Chambre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChambreDto {
    private Long id;
    private String nom;
    private String type;
    private int capacite;
    private String codeGouvernorat;
  	private String  codePrison;
  	private String  namePrison ;
  	
  	
 // Méthode de conversion depuis l'entité Chambre
    public static ChambreDto fromEntity(Chambre chambre) {
        if (chambre == null) return null;

        return ChambreDto.builder()
                .id(chambre.getId())
                .nom(chambre.getName())
                .type(chambre.getType())
                .capacite(chambre.getCapacite())
                .codeGouvernorat(chambre.getCodeGouvernorat())
                .codePrison(chambre.getCodePrison())
                .namePrison(chambre.getNamePrison())
                .build();
    }
}
