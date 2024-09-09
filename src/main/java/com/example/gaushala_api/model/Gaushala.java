package com.example.gaushala_api.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Gaushala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String registrationNumber;
    private String location;
    private String dungAmount;
    private String phoneNumber;
    private String userId;
    private String password; // Ensure you hash this before storing it

    // You can add additional fields if needed
}
