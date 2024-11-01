package com.example.refining_gaushala_app.models;

public class Request {
    private String bioplantName;
    private int dungAmount;
    private String requestDate; // Format: "dd/MM/yyyy" or any desired format
    private String dungType; // New field for dung type

    // Constructor
    public Request(String bioplantName, int dungAmount, String requestDate, String dungType) {
        this.bioplantName = bioplantName;
        this.dungAmount = dungAmount;
        this.requestDate = requestDate;
        this.dungType = dungType; // Initialize dung type
    }

    // Getters
    public String getBioplantName() {
        return bioplantName;
    }

    public int getDungAmount() {
        return dungAmount;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getDungType() {
        return dungType; // Getter for dung type
    }
}
