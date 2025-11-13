package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.DetenuParticipant;
import com.cgpr.visitApp.model.SanctionParticipant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SanctionParticipantDto {
    private String idPrisoner;
    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;
    private String nom;
    private String prenom;
    
    
    // Méthode de conversion depuis l'entité SanctionParticipant
    public static SanctionParticipantDto fromEntity(SanctionParticipant sp) {
        if (sp == null || sp.getParticipant() == null) return null;

        DetenuParticipant dp = sp.getParticipant();

        return SanctionParticipantDto.builder()
                .idPrisoner(dp.getIdPrisoner())
                .codeGouvernorat(dp.getCodeGouvernorat())
                .codePrison(dp.getCodePrison())
                .namePrison(dp.getNamePrison())
                .codeResidance(dp.getCodeResidance())
                .anneeResidance(dp.getAnneeResidance())
                .nom(dp.getNom())
                .prenom(dp.getPrenom())
                .build();
    }
}
