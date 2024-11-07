package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.BiogasPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiogasPlantRepository extends JpaRepository<BiogasPlant, Long> {
    BiogasPlant findByUserId(String userId);

   // List<Object> findById(String userId);
}
