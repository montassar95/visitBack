package com.cgpr.visitApp.repository.gestionChombre;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.TypeAudience;
import com.cgpr.visitApp.model.gestionChombre.Prison;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Repository
public interface PrisonRepository extends JpaRepository<Prison, Long>  {
	
    // Méthode automatique par nom de Spring Data JPA
    List<Prison> findByCodeGouAndCodePr(String codeGou, String codePr);

}
