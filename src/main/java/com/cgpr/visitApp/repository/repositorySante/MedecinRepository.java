package com.cgpr.visitApp.repository.repositorySante;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cgpr.visitApp.model.Medecin;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByFullName(String fullName);
}