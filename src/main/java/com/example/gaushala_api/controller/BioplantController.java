package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.BiogasPlant;
import com.example.gaushala_api.service.BiogasPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bioplant")
public class BioplantController {

    @Autowired
    private BiogasPlantService biogasPlantService;

    // Register a new Biogas Plant
    @PostMapping("/register")
    public BiogasPlant registerBioplant(@RequestBody BiogasPlant biogasPlant) {
        // You might want to hash the password before saving it
        return biogasPlantService.registerBiogasPlant(biogasPlant);
    }

    // Login a Biogas Plant
    @PostMapping("/login")
    public String loginBioplant(@RequestBody BiogasPlant biogasPlant) {
        boolean isAuthenticated = biogasPlantService.authenticate(biogasPlant.getUserId(), biogasPlant.getPassword());
        if (isAuthenticated) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }
}
