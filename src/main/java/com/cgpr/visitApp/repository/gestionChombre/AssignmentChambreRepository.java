package com.cgpr.visitApp.repository.gestionChombre;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.dto.CentreWithTotalDto;
import com.cgpr.visitApp.dto.ChambreWithTotalDto;
import com.cgpr.visitApp.dto.ComplexeWithTotalDto;
import com.cgpr.visitApp.dto.PavillonWithTotalDto;
import com.cgpr.visitApp.dto.PrisonWithTotalDto;
import com.cgpr.visitApp.model.AssignmentChambre;
import com.cgpr.visitApp.model.Hospitalisation;
 

@Repository
public interface AssignmentChambreRepository  extends JpaRepository<AssignmentChambre, Long>  {
//    // Toutes les assignations d’un prisonnier
//    List<AssignmentChambre> findByPrisoner_IdPrisoner(String prisonerId);
    
    
	@Query("SELECT a FROM AssignmentChambre a " +
		       "WHERE a.prisoner.idPrisoner = :prisonerId " +
		       "ORDER BY CASE WHEN a.endDate IS NULL THEN 1 ELSE 0 END, " +
		       "a.startDate  , a.endDate  ")
		List<AssignmentChambre> findByPrisoner_IdPrisoner(@Param("prisonerId") String prisonerId);


    // Toutes les assignations pour une chambre donnée
    List<AssignmentChambre> findByChambre_Id(Long chambreId);
    
    @Query("SELECT h FROM AssignmentChambre h " +
            "WHERE h.prisoner.idPrisoner = :prisonerId " +
            "AND h.endDate IS NULL")
     List<AssignmentChambre> findOngoingByPrisoner(@Param("prisonerId") String prisonerId);
    
    
//    @Query("SELECT a FROM AssignmentChambre a " +
//    	       "WHERE a.prisoner.idPrisoner = :prisonerId " +
//    	       
//    	       "AND (a.endDate IS NULL OR a.endDate >= :newStart) " +
//    	       "AND a.startDate <= COALESCE(:newEnd, a.startDate)")
//    	List<AssignmentChambre> findOverlapping(
//    	    @Param("prisonerId") String prisonerId,
//    	    @Param("newStart") LocalDate newStart,
//    	    @Param("newEnd") LocalDate newEnd 
//    	);

    
    @Query("SELECT a FROM AssignmentChambre a " +
    	       "WHERE a.prisoner.idPrisoner = :prisonerId " +
    	       "AND (a.endDate IS NULL OR a.endDate >= :newStart) " +
    	       "AND a.startDate <= COALESCE(:newEnd, a.startDate) " +
    	       "AND (:excludeId IS NULL OR a.id <> :excludeId)")
    	List<AssignmentChambre> findOverlapping(
    	    @Param("prisonerId") String prisonerId,
    	    @Param("newStart") LocalDate newStart,
    	    @Param("newEnd") LocalDate newEnd,
    	    @Param("excludeId") Long excludeId
    	);

    
//    @Query("SELECT new com.cgpr.visitApp.dto.PrisonWithTotalDto(" +
//    	       "p.id, p.codeGou, p.codePr, p.name, COUNT(a)) " +
//    	       "FROM Prison p " +
//    	       "LEFT JOIN AssignmentChambre a " +
//    	       "ON a.chambre.pavillon.complexe.centre.prison = p AND a.endDate IS NULL " +
//    	       "GROUP BY p.id, p.codeGou, p.codePr, p.name")
//    	List<PrisonWithTotalDto> getPrisonsWithActiveAffectations();

    
    
    @Query("SELECT new com.cgpr.visitApp.dto.PrisonWithTotalDto(" +
    	       "p.id, p.codeGou, p.codePr, p.name, p.type ,  COUNT(a) ,  COUNT(a) ,  COUNT(a) , COUNT(a), 0) " +
    	       "FROM Prison p " +
    	       "LEFT JOIN Centre ce ON ce.prison = p " +
    	       "LEFT JOIN Complexe co ON co.centre = ce " +
    	       "LEFT JOIN Pavillon pv ON pv.complexe = co " +
    	       "LEFT JOIN Chambre c ON c.pavillon = pv " +
    	       "LEFT JOIN AssignmentChambre a ON a.chambre = c AND a.endDate IS NULL " +
    	       "GROUP BY p.id, p.codeGou, p.codePr, p.type , p.name order by p.name")
    	List<PrisonWithTotalDto> getPrisonsWithActiveAffectations();

    @Query("SELECT new com.cgpr.visitApp.dto.CentreWithTotalDto(" +
    	       "ce.id,  ce.name,   COUNT(a) ,  COUNT(a) ,  COUNT(a) , COUNT(a)) " +
    	       "FROM Centre ce " +
    	       "LEFT JOIN Complexe co ON co.centre = ce " +
    	       "LEFT JOIN Pavillon pv ON pv.complexe = co " +
    	       "LEFT JOIN Chambre c ON c.pavillon = pv " +
    	       "LEFT JOIN AssignmentChambre a ON a.chambre = c AND a.endDate IS NULL " +
    	       "WHERE ce.prison.id = :prisonId " +
    	       "GROUP BY ce.id , ce.name order by ce.name")
    	List<CentreWithTotalDto> getCentresWithActiveAffectations(@Param("prisonId") Long prisonId);
    
    @Query("SELECT new com.cgpr.visitApp.dto.ComplexeWithTotalDto(" +
    	       "co.id, co.name,  COUNT(a) ,  COUNT(a) ,  COUNT(a) , COUNT(a) ) " +
    	       "FROM Complexe co " +
    	       "LEFT JOIN Pavillon pv ON pv.complexe = co " +
    	       "LEFT JOIN Chambre c ON c.pavillon = pv " +
    	       "LEFT JOIN AssignmentChambre a ON a.chambre = c AND a.endDate IS NULL " +
    	       "WHERE co.centre.id = :centreId " +
    	       "GROUP BY co.id, co.name order by co.name")
     List<ComplexeWithTotalDto> getComplexesWithActiveAffectations(@Param("centreId") Long centreId);
    
    
    
 // Méthode pour les pavillons d'un complexe
    @Query("SELECT new com.cgpr.visitApp.dto.PavillonWithTotalDto(" +
           "pv.id, pv.name,  COUNT(a) ,  COUNT(a) ,  COUNT(a) , COUNT(a)) " +
           "FROM Pavillon pv " +
           "LEFT JOIN Chambre c ON c.pavillon = pv " +
           "LEFT JOIN AssignmentChambre a WITH a.chambre = c AND a.endDate IS NULL " +
           "WHERE pv.complexe.id = :complexeId " +
           "GROUP BY pv.id, pv.name order by pv.name")
    List<PavillonWithTotalDto> getPavillonsWithActiveAffectations(@Param("complexeId") Long complexeId);

    // Méthode pour les chambres d'un pavillon
    @Query("SELECT new com.cgpr.visitApp.dto.ChambreWithTotalDto(" +
           "c.id, c.name,  COUNT(a) ,  COUNT(a) ,  COUNT(a) , COUNT(a)) " +
           "FROM Chambre c " +
           "LEFT JOIN AssignmentChambre a WITH a.chambre = c AND a.endDate IS NULL " +
           "WHERE c.pavillon.id = :pavillonId " +
           "GROUP BY c.id, c.name order by c.name")
    List<ChambreWithTotalDto> getChambresWithActiveAffectations(@Param("pavillonId") Long pavillonId);
    
    
    @Query("SELECT a FROM AssignmentChambre a " +
            "WHERE a.prisoner.id = :prisonerId " +
            "AND a.startDate <= :date " +
            "AND (a.endDate IS NULL OR a.endDate >= :date)")
   AssignmentChambre findByPrisonerAndDate(
             @Param("prisonerId") String prisonerId,
             @Param("date") LocalDate date);

    
    @Query("SELECT a FROM AssignmentChambre a " +
    	       "WHERE a.prisoner.idPrisoner = :prisonerId " +
    	       "AND a.startDate < :startDate " +
    	       "ORDER BY a.startDate DESC")
    	List<AssignmentChambre> findPreviousAffectations(
    	        @Param("prisonerId") String prisonerId,
    	        @Param("startDate") LocalDate startDate);

 }
