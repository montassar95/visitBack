package com.cgpr.visitApp.dto;

 

import com.cgpr.visitApp.model.TypeSanction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeSanctionDto {

    private Long id;
    private String label;
    private String classement;

    // Conversion Entity → DTO
    public static TypeSanctionDto fromEntity(TypeSanction entity) {
        if (entity == null) return null;
        return TypeSanctionDto.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .classement(entity.getClassement())
                .build();
    }

    // Conversion DTO → Entity
    public static TypeSanction toEntity(TypeSanctionDto dto) {
        if (dto == null) return null;
        TypeSanction entity = new TypeSanction();
        entity.setId(dto.getId());
        entity.setLabel(dto.getLabel());
        entity.setClassement(dto.getClassement());
        return entity;
    }
}
