package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.RaisonSanction;
import com.cgpr.visitApp.model.SanctionRaisonSanction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SanctionRaisonSanctionDto {
    private Long id;
    private String label;
    

    // Méthode de conversion depuis l'entité SanctionRaisonSanction
    public static SanctionRaisonSanctionDto fromEntity(SanctionRaisonSanction srs) {
        if (srs == null || srs.getRaisonSanction() == null) return null;

        RaisonSanction rs = srs.getRaisonSanction();

        return SanctionRaisonSanctionDto.builder()
                .id(rs.getId())
                .label(rs.getLabel())
                .build();
    }
}
