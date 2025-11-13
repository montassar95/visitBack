package com.cgpr.visitApp.model.gestionSecurityZone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SecurityCenterRepository extends JpaRepository<SecurityCenter, Long> {

    // Méthode personnalisée : liste des centres d'une zone
    @Query("SELECT c FROM SecurityCenter c WHERE c.zone.id = :zoneId")
    List<SecurityCenter> findCenterByZone(@Param("zoneId") Long zoneId);
}

