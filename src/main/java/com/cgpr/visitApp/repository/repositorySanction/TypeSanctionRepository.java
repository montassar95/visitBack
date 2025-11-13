package com.cgpr.visitApp.repository.repositorySanction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgpr.visitApp.model.RaisonSanction;
import com.cgpr.visitApp.model.TypeSanction;

public interface TypeSanctionRepository  extends JpaRepository<TypeSanction, Long>  {

}
