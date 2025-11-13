package com.cgpr.visitApp.repository.repositorySante;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cgpr.visitApp.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String name);
}
