package com.example.gaushala_api.service;

import com.example.gaushala_api.model.BiogasPlant;
import com.example.gaushala_api.repository.BiogasPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiogasPlantService {

    @Autowired
    private BiogasPlantRepository biogasPlantRepository;

    public BiogasPlant registerBiogasPlant(BiogasPlant biogasPlant) {
        // You might want to hash the password before saving it
        return biogasPlantRepository.save(biogasPlant);
    }

    public boolean authenticate(String userId, String password) {
        BiogasPlant biogasPlant = biogasPlantRepository.findByUserId(userId);
        return biogasPlant != null && biogasPlant.getPassword().equals(password);
    }

//    public BiogasPlant fetch(String userId){
//        return biogasPlantRepository.findByUserId(userId);
//        //return biogasPlantRepository.findAll();
//    }

}
