package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.SanctionTypeSanctionDuree;
import com.cgpr.visitApp.model.TypeSanction;
import com.cgpr.visitApp.model.TypeSanctionDuree;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SanctionTypeSanctionDureeDto {

    private TypeSanctionDto typeSanction; // objet complet
    private Integer duree;

    // Conversion Entity → DTO
    public static SanctionTypeSanctionDureeDto fromEntity(SanctionTypeSanctionDuree sts) {
        if (sts == null || sts.getTypeSanctionDuree() == null) return null;

        // Récupérer le TypeSanctionDuree et le TypeSanction
        TypeSanctionDuree tsd = sts.getTypeSanctionDuree();
        TypeSanction ts = tsd.getTypeSanction();

        return SanctionTypeSanctionDureeDto.builder()
                .typeSanction(TypeSanctionDto.fromEntity(ts)) // conversion en DTO
                .duree(tsd.getDuree())
                .build();
    }

    // Conversion DTO → Entity (optionnel)
    public static SanctionTypeSanctionDuree toEntity(SanctionTypeSanctionDureeDto dto) {
        if (dto == null || dto.getTypeSanction() == null) return null;

        TypeSanction ts = TypeSanctionDto.toEntity(dto.getTypeSanction());

        TypeSanctionDuree tsd = new TypeSanctionDuree();
        tsd.setTypeSanction(ts);
        tsd.setDuree(dto.getDuree());

        SanctionTypeSanctionDuree sts = new SanctionTypeSanctionDuree();
        sts.setTypeSanctionDuree(tsd);
        return sts;
    }
    
//    private Long typeSanctionId;
//    private String label;
//    private Integer duree;
//    
//    
//    // Méthode de conversion depuis l'entité SanctionTypeSanctionDuree
//    public static SanctionTypeSanctionDureeDto fromEntity(SanctionTypeSanctionDuree sts) {
//        if (sts == null || sts.getTypeSanctionDuree() == null) return null;
//
//        TypeSanctionDuree tsd = sts.getTypeSanctionDuree();
//        TypeSanction ts = tsd.getTypeSanction();
//
//        return SanctionTypeSanctionDureeDto.builder()
//                .typeSanctionId(ts.getId())
//                .label(ts.getLabel())
//                .duree(tsd.getDuree())
//                .build();
//    }
  }
