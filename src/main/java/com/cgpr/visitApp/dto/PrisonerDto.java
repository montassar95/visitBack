package com.cgpr.visitApp.dto;

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
    private String lastName;
    private String fatherName;
    private String grandFatherName;

    private String codeNationalite;
    private String nationalite;

    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;
    private String statutResidance;
    private String numDetention;
    private String eventDate;

    private String etat;
    private String situation;
    private String type;

    private String dateDebutPunition;
    private String dateFinPunition;

    // === Champs supplémentaires ajoutés depuis la requête ===
    private String birthDate;
    private String motherName;
    private String maternalGrandmotherName;
    private String sex;
    private String adresse;
    private String partenaire;
    private String nombreEnfant;
    private String profession;
    private String niveauCulturel;
    
    private String dateLiberation;
    private String motifLiberation;
    private String statutFermer;
    private String nombreMutation;
    
    
    
    // Nouveaux champs pour affichage hiérarchique
    private String chambreNom;
    private String pavillonNom;
    private String complexeNom;
    private String centreNom;
    private String prisonNom;
    private String endDate;
    
    private String  prisonOrig;
    // === Méthode pour convertir depuis l’entité ===
    public static PrisonerDto fromEntity(Prisoner prisoner) {
        if (prisoner == null) {
            return null;
        }

        return PrisonerDto.builder()
                .idPrisoner(prisoner.getIdPrisoner())
                .firstName(prisoner.getFirstName())
                .lastName(prisoner.getLastName())
                .fatherName(prisoner.getFatherName())
                .grandFatherName(prisoner.getGrandFatherName())
                .codeNationalite(prisoner.getCodeNationalite())
                .dateDebutPunition(prisoner.getDateDebutPunition())
                .dateFinPunition(prisoner.getDateFinPunition())
                .build();
    }

    // === Méthode pour convertir vers l’entité ===
    public static Prisoner toEntity(PrisonerDto dto) {
        if (dto == null) {
            return null;
        }

        return Prisoner.builder()
                .idPrisoner(dto.getIdPrisoner())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .fatherName(dto.getFatherName())
                .grandFatherName(dto.getGrandFatherName())
                .codeNationalite(dto.getCodeNationalite())
                .dateDebutPunition(dto.getDateDebutPunition())
                .dateFinPunition(dto.getDateFinPunition())
                .build();
    }
}
