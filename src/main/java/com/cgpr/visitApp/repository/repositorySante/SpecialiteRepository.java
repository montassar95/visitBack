package com.cgpr.visitApp.repository.repositorySante;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cgpr.visitApp.model.Specialite;

public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    Optional<Specialite> findByName(String name);
}