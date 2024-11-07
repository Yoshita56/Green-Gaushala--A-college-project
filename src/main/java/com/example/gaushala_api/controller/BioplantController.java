package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.BiogasPlant;
import com.example.gaushala_api.service.BiogasPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> loginBioplant(@RequestBody BiogasPlant biogasPlant) {
        boolean isAuthenticated = biogasPlantService.authenticate(biogasPlant.getUserId(), biogasPlant.getPassword());

        if (isAuthenticated) {
            // Returning a JSON response with a success message
            return ResponseEntity.ok().body("{\"message\": \"Login Successful\"}");
        } else {
            // Returning a JSON response with a failure message and status 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Login Failed\"}");
        }
    }
//    @GetMapping("/showProfile/{id}")
//    public BiogasPlant getStudent(@PathVariable String id) {
//        BiogasPlant std= biogasPlantService.fetch(id);
//        //fetching the student details by their resp roll.no.
//        return std;
//    }

}
