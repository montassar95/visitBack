package com.cgpr.visitApp.repository.repositorySanction;

 

import com.cgpr.visitApp.model.DetenuParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DetenuParticipantRepository extends JpaRepository<DetenuParticipant, Long> {

	  @Query("SELECT d FROM DetenuParticipant d " +
	           "WHERE (:idPrisoner IS NULL OR d.idPrisoner = :idPrisoner) " +
	           "AND (:codeGouvernorat IS NULL OR d.codeGouvernorat = :codeGouvernorat) " +
	           "AND (:codePrison IS NULL OR d.codePrison = :codePrison) " +
	           "AND (:codeResidance IS NULL OR d.codeResidance = :codeResidance) " +
	           "AND (:anneeResidance IS NULL OR d.anneeResidance = :anneeResidance)")
	    List<DetenuParticipant> searchByMultipleFields(
	            @Param("idPrisoner") String idPrisoner,
	            @Param("codeGouvernorat") String codeGouvernorat,
	            @Param("codePrison") String codePrison,
	            @Param("codeResidance") String codeResidance,
	            @Param("anneeResidance") String anneeResidance
	    );
}