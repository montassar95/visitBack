package com.cgpr.visitApp.repository.gestionChombre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgpr.visitApp.model.gestionChombre.PrisonerCategory;

@Repository
public interface PrisonerCategoryRepository extends JpaRepository<PrisonerCategory, Long> {}
