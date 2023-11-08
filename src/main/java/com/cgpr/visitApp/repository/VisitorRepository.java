package com.cgpr.visitApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.Visitor;
 
@Repository
public interface  VisitorRepository extends JpaRepository<Visitor, String> {
  
	
	 // Requête personnalisée pour rechercher les visiteurs par l'ID du prisonnier sans duplication
  //  @Query("SELECT DISTINCT v FROM Visitor v JOIN v.relationships r WHERE r.prisoner.idPrisoner = :prisonerId")
	@Query("SELECT DISTINCT v FROM Visitor v WHERE v.idVisitor IN (SELECT rv.visitor.idVisitor FROM RelationshipType rv WHERE rv.prisoner.idPrisoner = :prisonerId)")
    List<Visitor> findDistinctVisitorsByPrisonerId(String prisonerId);
}
