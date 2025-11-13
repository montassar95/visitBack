package com.cgpr.visitApp.repository.repositorySante;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cgpr.visitApp.model.EtatUrgence;

public interface EtatUrgenceRepository extends JpaRepository<EtatUrgence, Long> {
    Optional<EtatUrgence> findByLabel(String label);
}
