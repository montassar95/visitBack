package com.cgpr.visitApp.dto;

 

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.cgpr.visitApp.model.Sanction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SanctionDto {

	private Long id;

    // Identité du détenu
    private Long prisonerId;
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String lastName;
    private String codeNationalite;

    // Informations sur la prison et la résidence
    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;
    private LocalDate dateDebutResidance;

    // Informations sur la sanction
    private LocalDate dateConseil;
    private String numeroReference;
    private String jourIncident;
    private String sanctionText;
    private String infractionText;

    // Sélections
    private ChambreDto chambreIsolement;
    private ChambreDto chambreTransfert;

    // Listes dynamiques
    private List<SanctionParticipantDto> sanctionParticipants;
    private List<SanctionRapporteurDto> sanctionRapporteurs;
    private List<SanctionTypeSanctionDureeDto> sanctionTypeSanctionDuree;
    private List<SanctionRaisonSanctionDto> sanctionRaisonsSanction;
                                           
    public static SanctionDto convertToDto(Sanction sanction) {
        if (sanction == null) return null;

        return SanctionDto.builder()
                .id(sanction.getId())
                // Prisonnier
                .prisonerId(sanction.getPrisoner() != null ? Long.parseLong(sanction.getPrisoner().getIdPrisoner()) : null)
                .firstName(sanction.getPrisoner() != null ? sanction.getPrisoner().getFirstName() : null)
                .fatherName(sanction.getPrisoner() != null ? sanction.getPrisoner().getFatherName() : null)
                .grandFatherName(sanction.getPrisoner() != null ? sanction.getPrisoner().getGrandFatherName() : null)
                .lastName(sanction.getPrisoner() != null ? sanction.getPrisoner().getLastName() : null)
                .codeNationalite(sanction.getPrisoner() != null ? sanction.getPrisoner().getCodeNationalite() : null)

                // Infos prison / résidence
                .codeGouvernorat(sanction.getCodeGouvernorat())
                .codePrison(sanction.getCodePrison())
                .namePrison(sanction.getNamePrison())
                .codeResidance(sanction.getCodeResidance())
                .anneeResidance(sanction.getAnneeResidance())
                .dateDebutResidance(null) // si besoin, ajouter champ dateDebutResidance dans Sanction

                // Infos sanction
                .dateConseil(sanction.getDateConseil())
                .numeroReference(sanction.getNumeroReference())
                .jourIncident(sanction.getJourIncident())
                .sanctionText(sanction.getSanctionText())
                .infractionText(sanction.getInfractionText())

                // Chambres
                .chambreIsolement(sanction.getChambreIsolement() != null 
                        ? ChambreDto.fromEntity(sanction.getChambreIsolement()) : null)
                .chambreTransfert(sanction.getChambreTransfert() != null 
                        ? ChambreDto.fromEntity(sanction.getChambreTransfert()) : null)

                // Listes
                .sanctionParticipants(sanction.getSanctionParticipants() != null 
                        ? sanction.getSanctionParticipants().stream()
                            .map(SanctionParticipantDto::fromEntity)
                            .collect(Collectors.toList()) 
                        : null)
                .sanctionRapporteurs(sanction.getSanctionRapporteurs() != null
                        ? sanction.getSanctionRapporteurs().stream()
                            .map(SanctionRapporteurDto::fromEntity)
                            .collect(Collectors.toList())
                        : null)
                .sanctionTypeSanctionDuree(sanction.getSanctionTypeSanctionDurees() != null
                        ? sanction.getSanctionTypeSanctionDurees().stream()
                            .map(SanctionTypeSanctionDureeDto::fromEntity)
                            .collect(Collectors.toList())
                        : null)
                .sanctionRaisonsSanction(sanction.getSanctionRaisonsSanction() != null
                        ? sanction.getSanctionRaisonsSanction().stream()
                            .map(SanctionRaisonSanctionDto::fromEntity)
                            .collect(Collectors.toList())
                        : null)
                .build();
    }
}
