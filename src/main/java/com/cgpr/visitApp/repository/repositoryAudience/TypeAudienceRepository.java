package com.cgpr.visitApp.repository.repositoryAudience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cgpr.visitApp.model.TypeAudience;

@Repository
public interface TypeAudienceRepository extends JpaRepository<TypeAudience, Long> {
}
