package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.BiogasPlant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiogasPlantRepository extends JpaRepository<BiogasPlant, Long> {
    BiogasPlant findByUserId(String userId);
}
