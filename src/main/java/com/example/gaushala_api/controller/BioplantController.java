package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.BiogasPlant;
import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.repository.BiogasPlantRepository;
import com.example.gaushala_api.repository.GaushalaRepository;
import com.example.gaushala_api.service.BiogasPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bioplant")
public class BioplantController {

    @Autowired
    private BiogasPlantRepository biogasPlantRepository;

    @Autowired
    private BiogasPlantService biogasPlantService;

    @Autowired
    private GaushalaRepository gaushalaRepository;  // New dependency to interact with Gaushala

    // Register a new Biogas Plant
    @PostMapping("/register")
    public BiogasPlant registerBioplant(@RequestBody BiogasPlant biogasPlant) {
        // You might want to hash the password before saving it
        return biogasPlantService.registerBiogasPlant(biogasPlant);
    }

    // Login a Biogas Plant
    @PostMapping("/login")
    public ResponseEntity<String> loginBioplant(@RequestBody BiogasPlant biogasPlant) {
        boolean isAuthenticated = biogasPlantService.authenticate(biogasPlant.getUserId(), biogasPlant.getPassword());

        if (isAuthenticated) {
            Long bioplantId = biogasPlantService.findBioplantIdByUserId(biogasPlant.getUserId());
            String jsonResponse = String.format("{\"id\": %d, \"message\": \"Login Successful\"}", bioplantId);
            return ResponseEntity.ok().body(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Login Failed\"}");
        }
    }

    // Create a bioplant gas request
    @PostMapping("/request")
    public ResponseEntity<?> createBioplantGasRequest(@RequestBody BiogasPlant request) {
        biogasPlantService.createBioplantGasRequest(request.getDungType(), request.getDungRequested());
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Gas request created successfully\"}");
    }

    // Get Biogas Plant by ID
    @GetMapping("/bioplant/{id}")
    public ResponseEntity<BiogasPlant> getBioplant(@PathVariable Long id) {
        BiogasPlant bioplant = biogasPlantService.getBiogasPlant(id);
        if (bioplant != null) {
            return ResponseEntity.ok(bioplant);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update a Biogas Plant
    @PostMapping("/bioplant/{id}")
    public ResponseEntity<?> updateBioplant(
            @PathVariable Long id,
            @RequestParam String dungType,
            @RequestParam Double dungRequested,
            @RequestParam String date,
            @RequestParam Long gaushalaId) {  // Added gaushalaId to associate the biogas plant with a Gaushala

        // Fetch the existing BiogasPlant by ID
        Optional<BiogasPlant> bioplantOptional = biogasPlantRepository.findById(id);

        // If BiogasPlant with the given ID doesn't exist, return an error response
        if (!bioplantOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bioplant not found");
        }

        BiogasPlant bioplant = bioplantOptional.get();

        // Fetch the Gaushala by ID
        Optional<Gaushala> gaushalaOptional = gaushalaRepository.findById(gaushalaId);

        // If Gaushala with the given ID doesn't exist, return an error response
        if (!gaushalaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gaushala not found");
        }

        // Get the Gaushala object and associate it with the BiogasPlant
        Gaushala gaushala = gaushalaOptional.get();
        bioplant.setGaushala(gaushala);

        // Update the fields of BiogasPlant
        bioplant.setDungType(dungType);
        bioplant.setDungRequested(dungRequested);
        bioplant.setStatus("pending");   // Automatically set the status to "pending"
        bioplant.setDate(date);           // Set the date from the request

        // Save the updated BiogasPlant
        biogasPlantRepository.save(bioplant);

        // Return a success response
        return ResponseEntity.ok("Bioplant updated successfully");
    }



    @PostMapping("/bioplant/{id}/updateStatus")
    public ResponseEntity<?> updateBioplantStatus(
            @PathVariable Long id,
            @RequestParam String dungType,
            @RequestParam Double dungRequested,
            @RequestParam String date,
            @RequestParam Long gaushalaId,
            @RequestParam String status) {  // Added status parameter

        // Fetch the existing BiogasPlant by ID
        Optional<BiogasPlant> bioplantOptional = biogasPlantRepository.findById(id);

        // If BiogasPlant with the given ID doesn't exist, return an error response
        if (!bioplantOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bioplant not found");
        }

        BiogasPlant bioplant = bioplantOptional.get();

        // Fetch the Gaushala by ID
        Optional<Gaushala> gaushalaOptional = gaushalaRepository.findById(gaushalaId);

        // If Gaushala with the given ID doesn't exist, return an error response
        if (!gaushalaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gaushala not found");
        }

        // Get the Gaushala object and associate it with the BiogasPlant
        Gaushala gaushala = gaushalaOptional.get();
        bioplant.setGaushala(gaushala);

        // Update the fields of BiogasPlant
        bioplant.setDungType(dungType);
        bioplant.setDungRequested(dungRequested);
        bioplant.setDate(date); // Set the date from the request
        bioplant.setStatus(status); // Set the status from the request

        // Save the updated BiogasPlant
        biogasPlantRepository.save(bioplant);

        // Return a success response
        return ResponseEntity.ok("Request accepted: " + status);
    }





    // Get all Biogas Plants
    @GetMapping("/biogas")
    public ResponseEntity<List<BiogasPlant>> getAllBiogasPlants() {
        List<BiogasPlant> biogasPlants = biogasPlantService.getAllBiogasPlant();
        return new ResponseEntity<>(biogasPlants, HttpStatus.OK);
    }
}
