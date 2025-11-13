package com.cgpr.visitApp.repository.gestionChombre;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.gestionChombre.Chambre;
 
@Repository
public interface ChambreRepository  extends JpaRepository<Chambre, Long>  {

	
	  // 🔍 Trouver les chambres par type, gouvernorat et prison
  List<Chambre> findByTypeAndCodeGouvernoratAndCodePrison(
          String type,
          String codeGouvernorat,
          String codePrison
  );
  
  
  
  
  List<Chambre> findByPavillonIdOrderByNameAsc(Long pavillonId);

}
 
