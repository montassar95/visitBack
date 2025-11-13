package com.cgpr.visitApp.repository;

 

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgpr.visitApp.model.Audience;
import com.cgpr.visitApp.model.Sanction;

public interface AudienceRepository extends JpaRepository<Audience, Long> {
    
    
    // Récupérer toutes les sanctions d’un prisonnier
    @Query("SELECT s FROM Audience s WHERE s.prisoner.idPrisoner = :prisonerId  ORDER BY s.dateAudience DESC ")
    List<Audience> findByPrisonerId(@Param("prisonerId") String prisonerId);
}

