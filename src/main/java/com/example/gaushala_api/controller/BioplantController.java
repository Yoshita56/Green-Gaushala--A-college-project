package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.BiogasPlant;
import com.example.gaushala_api.repository.BiogasPlantRepository;
import com.example.gaushala_api.service.BiogasPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bioplant")
public class BioplantController {

    @Autowired
    private BiogasPlantRepository biogasPlantRepository;

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

    // Create a bioplant gas request
    @PostMapping("/request")
    public ResponseEntity<?> createBioplantGasRequest(@RequestBody BiogasPlant request) {
        // Assuming that the request contains dungType and dungRequested
        biogasPlantService.createBioplantGasRequest(request.getDungType(), request.getDungRequested());
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Gas request created successfully\"}");
    }

    @PutMapping("/biogasplant/{id}")
    public ResponseEntity<BiogasPlant> updateBiogasPlant(
            @PathVariable Long id,
            @RequestParam String dungType,
            @RequestParam double dungRequested) {

        BiogasPlant updatedPlant = biogasPlantService.updateBiogasPlant(id, dungType, dungRequested);

        return ResponseEntity.ok(updatedPlant);
    }
}

