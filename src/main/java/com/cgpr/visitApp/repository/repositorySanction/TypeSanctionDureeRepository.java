package com.cgpr.visitApp.repository.repositorySanction;


import com.cgpr.visitApp.model.TypeSanctionDuree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TypeSanctionDureeRepository extends JpaRepository<TypeSanctionDuree, Long> {

    @Query("SELECT t FROM TypeSanctionDuree t " +
           "WHERE (:typeSanctionId IS NULL OR t.typeSanction.id = :typeSanctionId) " +
           "AND (:duree IS NULL OR t.duree = :duree)")
    List<TypeSanctionDuree> findByTypeSanctionIdAndDuree(
            @Param("typeSanctionId") Long typeSanctionId,
            @Param("duree") Integer duree
    );
}