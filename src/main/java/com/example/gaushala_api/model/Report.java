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

    private String area;
    private String timeSlot;
    private String location;
    private String reportedBy;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    // New fields to be added
    private String status = "pending";  // Default status is 'pending'

    private String acceptedBy;  // Stores the ID of the gaushala user who accepted the request

    // Getters and setters for new fields are handled by Lombok's @Data annotation
}
