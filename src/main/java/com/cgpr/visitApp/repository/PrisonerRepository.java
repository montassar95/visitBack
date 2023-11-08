package com.cgpr.visitApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.Prisoner;
 
@Repository
public interface  PrisonerRepository extends JpaRepository<Prisoner, String> {
  
	

}
