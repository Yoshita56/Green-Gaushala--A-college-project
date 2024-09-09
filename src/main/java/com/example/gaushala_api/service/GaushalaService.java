package com.example.gaushala_api.service;

import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.repository.GaushalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaushalaService {

    @Autowired
    private GaushalaRepository gaushalaRepository;

    public Gaushala registerGaushala(Gaushala gaushala) {
        // You might want to hash the password before saving it
        return gaushalaRepository.save(gaushala);
    }

    public boolean authenticate(String userId, String password) {
        Gaushala gaushala = gaushalaRepository.findByUserId(userId);
        return gaushala != null && gaushala.getPassword().equals(password);
    }
}
