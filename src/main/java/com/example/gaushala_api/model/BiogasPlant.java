package com.example.gaushala_api.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class  BiogasPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String registrationNumber;
    private String phoneNumber;
    private String userId;
    private String password; // Ensure you hash this before storing it
    private String dungType;
    private float dungRequested;
    // You can add additional fields if needed
}
