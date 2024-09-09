package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.service.GaushalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gaushala")
public class GaushalaController {

    @Autowired
    private GaushalaService gaushalaService;

    // Register a new Gaushala
    @PostMapping("/register")
    public Gaushala registerGaushala(@RequestBody Gaushala gaushala) {
        // You might want to hash the password before saving it
        return gaushalaService.registerGaushala(gaushala);
    }

    // Login a Gaushala
    @PostMapping("/login")
    public String loginGaushala(@RequestBody Gaushala gaushala) {
        boolean isAuthenticated = gaushalaService.authenticate(gaushala.getUserId(), gaushala.getPassword());
        if (isAuthenticated) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }
}
