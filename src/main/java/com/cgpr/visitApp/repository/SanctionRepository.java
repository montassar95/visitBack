package com.cgpr.visitApp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgpr.visitApp.model.Sanction;

public interface SanctionRepository extends JpaRepository<Sanction, Long> {

    // Récupérer toutes les sanctions d’un prisonnier
	@Query("SELECT s FROM Sanction s WHERE s.prisoner.idPrisoner = :prisonerId")
	List<Sanction> findByPrisonerId(@Param("prisonerId") String prisonerId);


//    // Récupérer les sanctions actives à une date donnée
//    @Query("SELECT s FROM Sanction s WHERE s.dateDebutSanction <= :date AND s.dateFinSanction >= :date")
//    List<Sanction> findActiveSanctions(@Param("date") Date date);
}