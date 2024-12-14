package com.example.gaushala_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class BiogasPlant {

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
    private double dungRequested;

    @ManyToOne
    @JoinColumn(name = "gaushala_id", referencedColumnName = "id")
    private Gaushala gaushala;

    @Column(nullable = false)
    private String status;  // Renamed to lowercase

    private String date;

    @PrePersist
    public void setDefaults() {
        if (status == null || status.isEmpty()) {
            this.status = "completed";
            System.out.println("Default status set to 'completed'");
        }
    }
}