package com.cgpr.visitApp.repository;

import java.util.List;

import com.cgpr.visitApp.model.RelationshipType;

public interface RelationshipTypeRepositoryCustom {
    List<RelationshipType> findByTimeDayPrisonWithOptionalNames(
        String time, String day, String codeGouvernorat, String codePrison,
        String nom, String prenom, String nomPere ,String idPrisoner, String numeroEcrou
    );
}
