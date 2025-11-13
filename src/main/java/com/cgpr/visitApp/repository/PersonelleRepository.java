package com.cgpr.visitApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.Personelle;
import com.cgpr.visitApp.model.Visitor;
 
@Repository
public interface  PersonelleRepository extends JpaRepository<Personelle, Long> {
 
	
	@Query("SELECT  p FROM Personelle p WHERE p.login = :login and p.pwd = :pwd")
	Optional<Personelle>  findByLoginAndPwd(String login, String pwd);
}
