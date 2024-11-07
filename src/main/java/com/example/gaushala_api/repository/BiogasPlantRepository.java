package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.BiogasPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BiogasPlantRepository extends JpaRepository<BiogasPlant, Long> {
    BiogasPlant findByUserId(String userId);
    // Custom query to find the ID by userId
    @Query("SELECT b.id FROM BiogasPlant b WHERE b.userId = :userId")
    Long findIdByUserId(@Param("userId") String userId);
}
