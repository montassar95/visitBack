package com.cgpr.visitApp.dto;

 import java.time.LocalDate;
import java.util.Date;

import com.cgpr.visitApp.model.Audience;
import com.cgpr.visitApp.model.Prisoner;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AudienceDto {
    private Long id;
    private Long prisonerId;

    // Infos Prisonnier
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String lastName;
    private String codeNationalite;

    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;
    private Date dateDebutResidance;

    private LocalDate dateAudience;

    // =====================
    // Références judiciaires
    // =====================

    // IDs pour persistance
    private Long tribunalId;
    private Long typeAudienceId;

    // Labels pour affichage
    private String tribunalName;
    private String typeAudienceLabel;

    public static AudienceDto convertToDto(Audience audience) {
        Prisoner p = audience.getPrisoner();
        return AudienceDto.builder()
                .id(audience.getId())
                .prisonerId(p != null ? Long.parseLong(p.getIdPrisoner()) : null)

                .firstName(p != null ? p.getFirstName() : null)
                .fatherName(p != null ? p.getFatherName() : null)
                .grandFatherName(p != null ? p.getGrandFatherName() : null)
                .lastName(p != null ? p.getLastName() : null)
                .codeNationalite(p != null ? p.getCodeNationalite() : null)

                .codeGouvernorat(audience.getCodeGouvernorat())
                .codePrison(audience.getCodePrison())
                .namePrison(audience.getNamePrison())
                .codeResidance(audience.getCodeResidance())
                .anneeResidance(audience.getAnneeResidance())
                .dateDebutResidance(null) // à compléter si besoin

                .dateAudience(audience.getDateAudience())

                // Mapping IDs + labels
                .tribunalId(audience.getTribunal() != null ? audience.getTribunal().getId() : null)
                .tribunalName(audience.getTribunal() != null ? audience.getTribunal().getName() : null)

                .typeAudienceId(audience.getTypeAudience() != null ? audience.getTypeAudience().getId() : null)
                .typeAudienceLabel(audience.getTypeAudience() != null ? audience.getTypeAudience().getLabel() : null)

                .build();
    }
}