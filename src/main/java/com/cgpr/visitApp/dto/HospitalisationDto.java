package com.cgpr.visitApp.dto;

import java.time.LocalDate;

import com.cgpr.visitApp.model.Hospitalisation;
import com.cgpr.visitApp.model.Prisoner;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalisationDto {

    private Long id;
    private String prisonerId;

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
    private LocalDate dateDebutResidance;

    // IDs pour persister
    private Long hospitalId;
    private Long specialiteId;
    private Long medecinId;
    private Long etatUrgenceId;

    // Labels pour affichage
    private String hospitalName;
    private String specialiteName;
    private String medecinName;
    private String etatUrgenceLabel;

    private LocalDate admissionDate;
    private LocalDate dischargeDate;

    // Conversion Entity → DTO
    public static HospitalisationDto convertToDto(Hospitalisation h) {
        Prisoner p = h.getPrisoner();

        return HospitalisationDto.builder()
                .id(h.getId())
                .prisonerId(p != null ? p.getIdPrisoner() : null)
                .firstName(p != null ? p.getFirstName() : null)
                .fatherName(p != null ? p.getFatherName() : null)
                .grandFatherName(p != null ? p.getGrandFatherName() : null)
                .lastName(p != null ? p.getLastName() : null)
                .codeNationalite(p != null ? p.getCodeNationalite() : null)
                .codeGouvernorat(h.getCodeGouvernorat())
                .codePrison(h.getCodePrison())
                .namePrison(h.getNamePrison())
                .codeResidance(h.getCodeResidance())
                .anneeResidance(h.getAnneeResidance())
                .dateDebutResidance(null)
                .hospitalId(h.getHospital() != null ? h.getHospital().getId() : null)
                .hospitalName(h.getHospital() != null ? h.getHospital().getName() : null)
                .specialiteId(h.getSpecialite() != null ? h.getSpecialite().getId() : null)
                .specialiteName(h.getSpecialite() != null ? h.getSpecialite().getName() : null)
                .medecinId(h.getMedecin() != null ? h.getMedecin().getId() : null)
                .medecinName(h.getMedecin() != null ? h.getMedecin().getFullName() : null)
                .etatUrgenceId(h.getEtatUrgence() != null ? h.getEtatUrgence().getId() : null)
                .etatUrgenceLabel(h.getEtatUrgence() != null ? h.getEtatUrgence().getLabel() : null)
                .admissionDate(h.getAdmissionDate())
                .dischargeDate(h.getDischargeDate())
                .build();
    }
}
