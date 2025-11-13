package com.cgpr.visitApp.repository.repositorySanction;

import com.cgpr.visitApp.model.Rapporteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RapporteurRepository extends JpaRepository<Rapporteur, Long> {

    @Query("SELECT r FROM Rapporteur r " +
           "WHERE (:numeroAdministartif IS NULL OR r.numeroAdministartif = :numeroAdministartif) " +
           "AND (:nom IS NULL OR r.nom = :nom) " +
           "AND (:prenom IS NULL OR r.prenom = :prenom) " +
           "AND (:codeGouvernorat IS NULL OR r.codeGouvernorat = :codeGouvernorat) " +
           "AND (:codePrison IS NULL OR r.codePrison = :codePrison) " )
    List<Rapporteur> searchByMultipleFields(
            @Param("numeroAdministartif") String numeroAdministartif,
            @Param("nom") String nom,
            @Param("prenom") String prenom,
            @Param("codeGouvernorat") String codeGouvernorat,
            @Param("codePrison") String codePrison 
    );
}