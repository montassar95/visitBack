package com.cgpr.visitApp.repository.repositoryAudience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cgpr.visitApp.model.Tribunal;

@Repository
public interface TribunalRepository extends JpaRepository<Tribunal, Long> {
}