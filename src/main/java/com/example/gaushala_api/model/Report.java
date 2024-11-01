package com.example.gaushala_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reports") // Optional: Specifies the table name in the database
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accepted_by_id") // Create a new column name to avoid conflict
    private Gaushala acceptedBy; // Refers to the Gaushala entity that accepted the report

    private String area;
    private String timeSlot;
    private String location;
    private String reportedBy;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    private String status = "pending";  // Default status is 'pending'

    // Getters and setters for new fields are handled by Lombok's @Data annotation
}
