package com.example.refining_gaushala_app.models;

import java.util.Date;

public class Bioplant {
    private long id; // Unique ID for Bioplant
    private String name;
    private String registrationNumber;
    private String location;
    // Gaushala details (you can return these as part of Bioplant)
    private Gaushala gaushala;  // Add Gaushala as an embedded object
    private Date enquiryDate;  // Date when the enquiry was made (nullable)
    private String status;     // Status of the enquiry (Pending, Completed, etc.)
    private String phoneNumber;
    private String userId; // Unique User ID for the bioplant
    private String password;
    private long gaushalaId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    private String date;

    public String getDate() {
        return date;
    }

    public void setOrderDate(String date) {
        this.date = date;
    }

    private String dungType;  // Dung Type (nullable)
    private Float dungRequested;  // Requested amount of dung (nullable)

    // Constructor with all fields


    public Bioplant(long id, String name, String registrationNumber, String location,
                    String phoneNumber, String userId, String password, String dungType, Float dungRequested, Date enquiryDate, Gaushala gaushala) {
        this.id = id;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.password = password;
        this.dungType = dungType;
        this.dungRequested = dungRequested;
        this.enquiryDate = enquiryDate;
        this.gaushala = gaushala;  // Initialize the Gaushala object
    }

    // Constructor with all fields except Gaushala
    public Bioplant(long id, String name, String registrationNumber, String location,
                    String phoneNumber, String userId, String password, String dungType, Float dungRequested, Date enquiryDate) {
        this.id = id;
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.password = password;
        this.dungType = dungType;
        this.dungRequested = dungRequested;
        this.enquiryDate = enquiryDate;
    }

    // Constructor without dungType, dungRequested, and enquiryDate for login or registration
    public Bioplant(long id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
    }

    // Constructor for creating an empty Bioplant object
    public Bioplant() {
        // No-argument constructor for creating an empty Bioplant object
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(Date enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDungType() {
        return dungType;
    }

    public void setDungType(String dungType) {
        this.dungType = dungType;
    }

    public Float getDungRequested() {
        return dungRequested;
    }

    public void setDungRequested(Float dungRequested) {
        this.dungRequested = dungRequested;
    }

    public long getGaushalaId() {
        return gaushalaId;
    }

    public void setGaushalaId(long gaushalaId) {
        this.gaushalaId = gaushalaId;
    }

    public Gaushala getGaushala() {
        return gaushala;
    }

    public void setGaushala(Gaushala gaushala) {
        this.gaushala = gaushala;
    }

    @Override
    public String toString() {
        return "Bioplant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", gaushala=" + gaushala + // Include Gaushala data for display
                ", dungType='" + dungType + '\'' +
                ", dungRequested=" + dungRequested +
                ", enquiryDate=" + enquiryDate +
                ", status='" + status + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
