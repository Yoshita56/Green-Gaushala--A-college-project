
package com.example.gaushala_api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.mapping.UniqueKey;

@Data
@Entity
@Table(name = "gaushala")
public class Gaushala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String registrationNumber;
    private String location;
    private String dungAmount;
    private String phoneNumber;
    private String email;
//    private String address;
//    private String city;
//    private String state;
//    private String zipCode;
//    private String country;


    @Column(unique = true)
    private String userId;
    private String password; // Ensure you hash this before storing it

    // The acceptedBy field is removed
    // You can add additional fields if needed
    private Double freshDungAmount; // Amount of fresh dung in kg
    private Double freshDungPrice;   // Price per kg of fresh dung
    private Double dryDungAmount;     // Amount of dry dung in kg
    private Double dryDungPrice;      // Price per kg of dry dung
}
