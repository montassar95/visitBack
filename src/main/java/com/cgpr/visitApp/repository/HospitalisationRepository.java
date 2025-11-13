package com.cgpr.visitApp.repository;
 
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cgpr.visitApp.model.Hospitalisation;

public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {

	// 🔹 Récupérer toutes les hospitalisations d’un prisonnier triées par date d'entrée
	@Query("SELECT h FROM Hospitalisation h WHERE h.prisoner.idPrisoner = :prisonerId ORDER BY h.admissionDate DESC")
	List<Hospitalisation> findByPrisonerId(@Param("prisonerId") String prisonerId);


//    // 🔹 Récupérer les hospitalisations actives à une date donnée
//    @Query("SELECT h FROM Hospitalisation h WHERE h.admissionDate <= :date AND h.dischargeDate >= :date")
//    List<Hospitalisation> findActiveHospitalisations(@Param("date") LocalDate date);
//    
    
 // Hospitalisations en cours
    @Query("SELECT h FROM Hospitalisation h " +
           "WHERE h.prisoner.idPrisoner = :prisonerId " +
           "AND h.dischargeDate IS NULL")
    List<Hospitalisation> findOngoingByPrisoner(@Param("prisonerId") String prisonerId);

    // Chevauchement de dates
    @Query("SELECT h FROM Hospitalisation h " +
           "WHERE h.prisoner.idPrisoner = :prisonerId " +
           "AND (h.dischargeDate IS NULL OR h.dischargeDate >= :newAdmission) " +
           "AND h.admissionDate <= COALESCE(:newDischarge, h.admissionDate)")
    List<Hospitalisation> findOverlapping(
        @Param("prisonerId") String prisonerId,
        @Param("newAdmission") LocalDate newAdmission,
        @Param("newDischarge") LocalDate newDischarge
    );



    
}
