package com.example.refining_gaushala_app.models;

public class OrderItem {

    private String status;
    private String orderDate;
    private double dungRequested;
    private String dungType;
    private String gaushalaName;
    private String remarks;

    // Constructor
    public OrderItem(String status, String orderDate, double dungRequested, String dungType, String gaushalaName, String remarks) {
        this.status = status;
        this.orderDate = orderDate;
        this.dungRequested = dungRequested;
        this.dungType = dungType;
        this.gaushalaName = gaushalaName;
        this.remarks = remarks;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getDungRequested() {
        return dungRequested;
    }

    public void setDungRequested(double dungRequested) {
        this.dungRequested = dungRequested;
    }

    public String getDungType() {
        return dungType;
    }

    public void setDungType(String dungType) {
        this.dungType = dungType;
    }

    public String getGaushalaName() {
        return gaushalaName;
    }

    public void setGaushalaName(String gaushalaName) {
        this.gaushalaName = gaushalaName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
