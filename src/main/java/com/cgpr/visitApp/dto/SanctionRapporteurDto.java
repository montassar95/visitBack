package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.Rapporteur;
import com.cgpr.visitApp.model.SanctionRapporteur;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SanctionRapporteurDto {
    private String numeroAdministratif;
    private String nom;
    private String prenom;
    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    

    

    // Méthode de conversion depuis l'entité SanctionRapporteur
    public static SanctionRapporteurDto fromEntity(SanctionRapporteur sr) {
        if (sr == null || sr.getRapporteur() == null) return null;

        Rapporteur r = sr.getRapporteur();

        return SanctionRapporteurDto.builder()
                .numeroAdministratif(r.getNumeroAdministartif())
                .nom(r.getNom())
                .prenom(r.getPrenom())
                .codeGouvernorat(r.getCodeGouvernorat())
                .codePrison(r.getCodePrison())
                .namePrison(r.getNamePrison())
                .build();
    }
}
