package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.Gaushala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GaushalaRepository extends JpaRepository<Gaushala, Long> {
    Gaushala findByUserId(String userId);
}
