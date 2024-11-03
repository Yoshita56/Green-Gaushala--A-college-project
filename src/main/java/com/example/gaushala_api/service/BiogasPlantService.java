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

    public BiogasPlant createBioplantGasRequest(String dungType, double dungRequested) {
        // Validation
        if (dungType == null || dungType.isEmpty()) {
            throw new IllegalArgumentException("Dung type cannot be null or empty.");
        }
        if (dungRequested <= 0) {
            throw new IllegalArgumentException("Dung requested must be greater than 0.");
        }

        // Create a new BiogasPlant object for the gas request
        BiogasPlant bioplantGas = new BiogasPlant();
        bioplantGas.setdungType(dungType); // Ensure the method name matches your model
        bioplantGas.setdungRequested(dungRequested); // Ensure the method name matches your model

        // Save to repository and return the saved object
        return biogasPlantRepository.save(bioplantGas);
    }

    public BiogasPlant updateBiogasPlant(Long id, String dungType, double dungRequested) {
        // Retrieve the existing BiogasPlant object by ID
        BiogasPlant existingPlant = biogasPlantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BiogasPlant not found with ID: " + id));

        // Update the fields
        existingPlant.setdungType(dungType);
        existingPlant.setdungRequested(dungRequested);

        // Save the updated object back to the database
        return biogasPlantRepository.save(existingPlant);
    }


}
