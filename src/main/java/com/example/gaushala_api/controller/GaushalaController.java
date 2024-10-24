package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.service.GaushalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> loginGaushala(@RequestBody Gaushala gaushala) {
        boolean isAuthenticated = gaushalaService.authenticate(gaushala.getUserId(), gaushala.getPassword());

        if (isAuthenticated) {
            // Returning a JSON response with a success message
            return ResponseEntity.ok().body("{\"message\": \"Login Successful\"}");
        } else {
            // Returning a JSON response with a failure message and status 401 (Unauthorized)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Login Failed\"}");
        }
    }

    // Get all Gaushalas
    @GetMapping("/gaushalas")
    public ResponseEntity<List<Gaushala>> getAllGaushalas() {
        List<Gaushala> gaushalas = gaushalaService.getAllGaushalas();
        return new ResponseEntity<>(gaushalas, HttpStatus.OK);
    }

}
