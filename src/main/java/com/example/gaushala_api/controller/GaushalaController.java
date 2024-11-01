package com.example.gaushala_api.controller;

import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.model.MessageResponse;
import com.example.gaushala_api.model.Report;
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
        return gaushalaService.registerGaushala(gaushala);
    }

    // Login a Gaushala
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Gaushala loginRequest) {
        Gaushala gaushala = gaushalaService.authenticate(loginRequest.getUserId(), loginRequest.getPassword());

        if (gaushala != null) {
            return ResponseEntity.ok(gaushala); // Return the Gaushala object
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid credentials")); // Return an error message
        }
    }



       



//    @PostMapping("/login")
//    public ResponseEntity<?> loginGaushala(@RequestBody Gaushala gaushala) {
//        boolean isAuthenticated = gaushalaService.authenticate(gaushala.getUserId(), gaushala.getPassword());
//
//        if (isAuthenticated) {
//            return ResponseEntity.ok().body("{\"message\": \"Login Successful\"}");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Login Failed\"}");
//        }
//    }

    // Get all Gaushalas
    @GetMapping("/gaushalas")
    public ResponseEntity<List<Gaushala>> getAllGaushalas() {
        List<Gaushala> gaushalas = gaushalaService.getAllGaushalas();
        return new ResponseEntity<>(gaushalas, HttpStatus.OK);
    }

    // Update dung details for a Gaushala
    @PutMapping("/update-dung/{id}")
    public ResponseEntity<?> updateDungDetails(
            @PathVariable Long id,
            @RequestBody Gaushala gaushala) {
        // Fetch the existing Gaushala by ID
        Gaushala existingGaushala = gaushalaService.getGaushalaById(id);
        if (existingGaushala == null) {
            return new ResponseEntity<>("{\"message\": \"Gaushala not found\"}", HttpStatus.NOT_FOUND);
        }

        // Update only the provided fields from the JSON payload
        if (gaushala.getFreshDungAmount() != null) existingGaushala.setFreshDungAmount(gaushala.getFreshDungAmount());
        if (gaushala.getDryDungAmount() != null) existingGaushala.setDryDungAmount(gaushala.getDryDungAmount());
        if (gaushala.getFreshDungPrice() != null) existingGaushala.setFreshDungPrice(gaushala.getFreshDungPrice());
        if (gaushala.getDryDungPrice() != null) existingGaushala.setDryDungPrice(gaushala.getDryDungPrice());

        // Save the updated Gaushala object
        try {
            Gaushala updatedGaushala = gaushalaService.updateGaushala(id, existingGaushala);
            return new ResponseEntity<>(updatedGaushala, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"message\": \"Error updating Gaushala details\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
