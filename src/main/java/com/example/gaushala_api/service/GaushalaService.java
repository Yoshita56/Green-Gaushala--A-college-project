package com.example.gaushala_api.service;

import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.repository.GaushalaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GaushalaService {

    @Autowired
    private GaushalaRepository gaushalaRepository;

    // Register a new Gaushala
    public Gaushala registerGaushala(Gaushala gaushala) {
        return gaushalaRepository.save(gaushala);
    }

    // Authenticate a Gaushala
    public Gaushala authenticate(String userId, String password) {
        Gaushala gaushala = gaushalaRepository.findByUserId(userId);
        if (gaushala != null && gaushala.getPassword().equals(password)) {
            return gaushala; // Return the authenticated Gaushala object
        }
        return null; // Return null if authentication fails
    }
//    public boolean authenticate(String userId, String password) {
//        Gaushala gaushala = gaushalaRepository.findByUserId(userId);
//        return gaushala != null && gaushala.getPassword().equals(password);
//    }

    // Get a Gaushala by ID
    public Gaushala getGaushalaById(Long id) {
        return gaushalaRepository.findById(id).orElse(null);
    }

    // Retrieve all Gaushalas
    public List<Gaushala> getAllGaushalas() {
        return gaushalaRepository.findAll();
    }

    // Update an existing Gaushala by ID and Gaushala object
    public Gaushala updateGaushala(Long id, Gaushala gaushalaDetails) {
        Optional<Gaushala> optionalGaushala = gaushalaRepository.findById(id);
        if (optionalGaushala.isPresent()) {
            Gaushala existingGaushala = optionalGaushala.get();

            // Update the existing Gaushala with new details
            existingGaushala.setName(gaushalaDetails.getName());
            existingGaushala.setRegistrationNumber(gaushalaDetails.getRegistrationNumber());
            existingGaushala.setLocation(gaushalaDetails.getLocation());
            existingGaushala.setDungAmount(gaushalaDetails.getDungAmount());
            existingGaushala.setPhoneNumber(gaushalaDetails.getPhoneNumber());
            existingGaushala.setUserId(gaushalaDetails.getUserId());
            existingGaushala.setPassword(gaushalaDetails.getPassword());
            existingGaushala.setFreshDungAmount(gaushalaDetails.getFreshDungAmount());
            existingGaushala.setFreshDungPrice(gaushalaDetails.getFreshDungPrice());
            existingGaushala.setDryDungAmount(gaushalaDetails.getDryDungAmount());
            existingGaushala.setDryDungPrice(gaushalaDetails.getDryDungPrice());

            return gaushalaRepository.save(existingGaushala);
        }
        return null;
    }
}
