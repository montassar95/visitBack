package com.cgpr.visitApp.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.EventSMSDto;
import com.cgpr.visitApp.dto.RelationshipSalleAndTimeDTO;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;
 
@Repository
public interface  RelationshipTypeRepository extends JpaRepository<RelationshipType, Long> , RelationshipTypeRepositoryCustom{
  
//	// Recherche tous les RelationshipType associés à un Prisoner donné
//    List<RelationshipType> findByPrisoner(Prisoner prisoner);
    
 // Recherche tous les RelationshipType associés à un id de prisoner donné

//	AND rt.statutResidance = 'O'
	
	@Query("SELECT rt FROM RelationshipType rt WHERE   rt.prisoner.idPrisoner = :idPrisoner  AND  UPPER(TRIM(rt.statutResidance)) = 'O'")
    List<RelationshipType> findVisitByPrisonerId(@Param("idPrisoner") String idPrisoner);

	 
	  
	@Query("SELECT rt FROM RelationshipType rt WHERE   rt.prisoner.idPrisoner = :idPrisoner  AND (UPPER(TRIM(rt.statutResidance)) = 'O' OR UPPER(TRIM(rt.statutResidance)) = 'M' OR UPPER(TRIM(rt.statutResidance)) = 'E') ")
    List<RelationshipType> findByPrisonerId(@Param("idPrisoner") String idPrisoner);

	  @Query("SELECT rt FROM RelationshipType rt " +
	           "WHERE rt.codeGouvernorat = :codeGouvernorat " +
	           "AND rt.codePrison = :codePrison " +
	           "AND rt.codeResidance = :codeResidance " +
	           "AND (SELECT COUNT(rt2) FROM RelationshipType rt2 " +
	                "WHERE rt2.codeGouvernorat = rt.codeGouvernorat " +
	                "AND rt2.codePrison = rt.codePrison " +
	                "AND rt2.codeResidance = rt.codeResidance) = 1 " +
	           "AND rt.statutResidance = 'E'")
	  RelationshipType  findSingleStatusRelationshipTypesE(
	            @Param("codeGouvernorat") String codeGouvernorat,
	            @Param("codePrison") String codePrison,
	            @Param("codeResidance") String codeResidance
	    );
	
//	AND rt.statutResidance = 'O'
	@Query("SELECT rt FROM RelationshipType rt WHERE rt.prisoner.idPrisoner = :idPrisoner AND (UPPER(TRIM(rt.statutResidance)) = 'O' OR UPPER(TRIM(rt.statutResidance)) = 'M' OR UPPER(TRIM(rt.statutResidance)) = 'E')")
	List<RelationshipType> findByPrisonerIdAndStatutOorMorE(@Param("idPrisoner") String idPrisoner);
	
	@Query("SELECT rt FROM RelationshipType rt WHERE "		  
		      + "rt.codeGouvernorat = :codeGouvernorat AND "
		      + "rt.codePrison = :codePrison AND "
		      + "rt.statutSMS IS NULL  AND "  // Ajout de " = 'READY' " ici
		      + "(UPPER(TRIM(rt.statutResidance))) = 'O' ")
		List<RelationshipType> findByPrisonAndStatutOAndStatutSMSReady(  
		                @Param("codeGouvernorat") String codeGouvernorat,
		                @Param("codePrison") String codePrison);

	
//	@Query("SELECT rt FROM RelationshipType rt WHERE "
//			 + "(:time IS NULL OR rt.time = :time) AND "
//		     + "(:day IS NULL OR rt.day = :day) AND "
//			+ "rt.codeGouvernorat = :codeGouvernorat AND "
//			+ "rt.codePrison = :codePrison AND "
//			+ "(UPPER(TRIM(rt.statutResidance))) = 'O' order by rt.day , rt.time  ")
//	 "  AND ide.TNUMIDE IN ( " +
//     "       SELECT simp.TNUMIDE FROM TIDENSIMP@LINK_PENALE simp " +
//     "       WHERE ( ? IS NULL OR simp.TNOMSA LIKE '%' || ? || '%' ) " +
//     "         AND ( ? IS NULL OR simp.TPNOMSA LIKE '%' || ? || '%') " +
//     "         AND ( ? IS NULL OR simp.TPPERSA LIKE '%' || ? || '%' ) " +
//
//     "  ) " +
//	List<RelationshipType> findByTimeAndDayAndPrisonAndStatutO(  
//								@Param("time") String time,
//					            @Param("day") String day,
//								@Param("codeGouvernorat") String codeGouvernorat,
//					            @Param("codePrison") String codePrison);
	
 



	
	@Query("SELECT rt FROM RelationshipType rt WHERE "
			+ "rt.codeGouvernorat = :codeGouvernorat AND "
			+ "rt.codePrison = :codePrison AND "
			+ "(UPPER(TRIM(rt.statutResidance))) <> 'O' AND"
			+ "(UPPER(TRIM(rt.statutResidance))) <> 'F'")
	List<RelationshipType> findByPrisonAndStatutNotO(  
								@Param("codeGouvernorat") String codeGouvernorat,
					            @Param("codePrison") String codePrison);
	
	@Query(value = "SELECT DISTINCT rela.* " +
		       "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide, relationship_type rela " +
		       "WHERE res.TCLORES = 'O' " +
		       "AND res.TNUMIDE = ide.TNUMIDE " +
		       "AND rela.prisoner_id = res.TNUMIDE " +
		       "AND (res.TCODRES <> rela.code_residance " +
		       "OR res.TCODGOU <> rela.code_gouvernorat " +
		       "OR res.TCODPR <> rela.code_prison) "+
		       "    AND (rela.statut_residance = 'O' OR rela.statut_residance = 'E' OR rela.statut_residance = 'M' )"  ,
		       nativeQuery = true)
		List<RelationshipType> findMutation();
	
 

	
	@Query(value = "SELECT DISTINCT r.* "
	        + "FROM TRESIDENCE@LINK_PENALE res, TIDENTITE@LINK_PENALE ide, TDETENTION@LINK_PENALE td, relationship_type r "
	        + "WHERE res.tclores = 'F' "
	        + "AND res.TDATFR = td.tdatlibe "
	        + "AND res.TNUMIDE = ide.TNUMIDE "
	        + "AND res.TNUMIDE = td.TNUMIDE "
	        + "AND res.tcoddet = td.tcoddet "
	        + "AND r.prisoner_id = ide.TNUMIDE "
	        + "AND r.statut_residance='O' "
	        + "AND r.num_detention = td.TCODDET "
	        + "AND td.tdatlibe is not null", nativeQuery = true)
	List<RelationshipType> findLiberation();


	
 // Méthode de suppression personnalisée pour supprimer par ID de Prisoner
//    @Modifying
//    @Query("DELETE FROM RelationshipType r WHERE r.prisoner.idPrisoner IN :prisonerIds AND r.statutResidance <> 'F' ")
//    void deleteByPrisonerIdIn(@Param("prisonerIds") Set<String> set);
	@Modifying
	@Query("UPDATE RelationshipType r SET r.statutResidance = 'F' WHERE r.prisoner.idPrisoner IN :prisonerIds AND r.statutResidance <> 'F' ")
	void updateStatutResidanceToF(@Param("prisonerIds") Set<String> set);

 // Compte le nombre de rendez-vous pour chaque combinaison jour/heure en fonction du codeGouvernorat et codePrison
    @Query("SELECT r.day, r.time, COUNT(DISTINCT r.prisoner.idPrisoner) " +
           "FROM RelationshipType r " +
           "WHERE r.codeGouvernorat = :codeGouvernorat AND r.codePrison = :codePrison AND r.statutResidance = 'O' AND r.centre = :centre AND r.salle = :salle " +
           "GROUP BY   r.day, r.time  ")
    List<Object[]> countRelationshipsByDayAndTime (
        @Param("codeGouvernorat") String codeGouvernorat,
        @Param("codePrison") String codePrison,
        @Param("centre") String centre,
        @Param("salle") String salle
    );
    // Compte le nombre de rendez-vous pour chaque combinaison jour/heure en fonction du codeGouvernorat et codePrison
    @Query("SELECT r.day, r.time, COUNT(DISTINCT r.prisoner.idPrisoner) " +
           "FROM RelationshipType r " +
           "WHERE r.codeGouvernorat = :codeGouvernorat AND r.codePrison = :codePrison AND r.statutResidance = 'O' " +
           "GROUP BY   r.day, r.time  ")
    List<Object[]> countRelationshipsByDayAndTimeForDashboarding (
        @Param("codeGouvernorat") String codeGouvernorat,
        @Param("codePrison") String codePrison  
    );
//    , r.prisoner.idPrisoner
    
    
    @Transactional
    @Modifying
    @Query("UPDATE RelationshipType r SET r.statutSMS = 'READY' WHERE r.statutResidance = :statutResidance AND r.prisoner.idPrisoner IN :prisonerIds")
    void updateStatutSMSByStatutResidanceAndPrisonerIds(@Param("statutResidance") String statutResidance, @Param("prisonerIds") List<String> prisonerIds);
    
//    r.statutSMS = 'READY'AND
    @Query("SELECT r FROM RelationshipType r WHERE r.statutSMS = 'READY' AND r.statutResidance <> 'F' AND r.libelleStatutResidance = :parametre")
    List<RelationshipType> findRelationshipTypesByStatutSMSAndLibelleStatutResidance(@Param("parametre") String parametre);

    
    
    @Query("SELECT rt FROM RelationshipType rt " +
            "WHERE rt.codeGouvernorat = :codeGouvernorat " +
            "AND rt.codePrison = :codePrison " +
            "AND rt.statutResidance = :statutResidance " +
            "AND ( rt.statutSMS  = :statutSMS)")
     List<RelationshipType> findByStatuses(@Param("codeGouvernorat") String codeGouvernorat,
                                        @Param("codePrison") String codePrison,
                                        @Param("statutResidance") String statutResidance,
                                        @Param("statutSMS") String statutSMS);
    
    @Query("SELECT rt FROM RelationshipType rt " +
            "WHERE rt.codeGouvernorat = :codeGouvernorat " +
            "AND rt.codePrison = :codePrison " +
            "AND rt.statutResidance = :statutResidance " +
            "AND  rt.statutSMS is null ")
     List<RelationshipType> findByStatuses(@Param("codeGouvernorat") String codeGouvernorat,
                                        @Param("codePrison") String codePrison,
                                        @Param("statutResidance") String statutResidance );
    
    
    @Query("SELECT rt FROM RelationshipType rt " +
            "WHERE rt.codeGouvernorat = :codeGouvernorat " +
            "AND rt.codePrison = :codePrison " +
            "AND rt.statutResidance = :statutResidance "+
            "AND  rt.statutSMS  = 'SENT' " +
            "AND TO_DATE(TO_CHAR(rt.sentDate, 'YYYY-MM-DD'), 'YYYY-MM-DD') BETWEEN :dateStart AND :dateEnd")
     List<RelationshipType> findByStatuses(@Param("codeGouvernorat") String codeGouvernorat,
                                        @Param("codePrison") String codePrison,
                                        @Param("statutResidance") String statutResidance,
                                        @Param("dateStart") Date dateStart,
                                        @Param("dateEnd") Date dateEnd);
    
    
    
    @Query("SELECT new com.cgpr.visitApp.dto.EventSMSDto(" +
            "r.prisoner.id, r.prisoner.firstName, r.prisoner.lastName, " +
            "r.relationShip, r.namePrison, r.numDetention, " +
            "r.codeResidance, r.anneeResidance, r.libelleStatutResidance, " +
            "r.eventDate, r.statutSMS, r.sentDate, " +
            "r.visitor.firstName, r.visitor.lastName, r.visitor.phone , r.statutDLR , r.dlrDate) " +
            "FROM RelationshipType r " +
            "WHERE  r.prisoner.id = :prisonerId AND r.statutResidance = 'O' ")
     List<EventSMSDto> findEntrantEventsByPrisonerId(@Param("prisonerId") String prisonerId); 
//    r.libelleStatutResidance = 'Entrant' AND

    
    
    
    
    @Query("SELECT new com.cgpr.visitApp.dto.RelationshipSalleAndTimeDTO(r.day, r.time, r.centre, r.salle) " +
            "FROM RelationshipType r " +
            "WHERE r.prisoner.id = :prisonerId " +
            "AND r.statutResidance = 'O' " +
            "AND r.id = (SELECT MAX(r2.id) FROM RelationshipType r2 WHERE r2.prisoner.id = :prisonerId AND r2.statutResidance = 'O')")
    RelationshipSalleAndTimeDTO findLastActiveResidenceByPrisonerId(@Param("prisonerId") String string);
  
}
