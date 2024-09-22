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
//    private String imageUrl;
    // Default constructor
//    public Report() {
//    }
//
//    // Parameterized constructor
//    public Report(String area, String timeSlot, String location, String reportedBy, String imageUrl) {
//        this.area = area;
//        this.timeSlot = timeSlot;
//        this.location = location;
//        this.reportedBy = reportedBy;
//        this.imageUrl = imageUrl;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public String getTimeSlot() {
//        return timeSlot;
//    }
//
//    public void setTimeSlot(String timeSlot) {
//        this.timeSlot = timeSlot;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getReportedBy() {
//        return reportedBy;
//    }
//
//    public void setReportedBy(String reportedBy) {
//        this.reportedBy = reportedBy;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "Report{" +
//                "id=" + id +
//                ", area='" + area + '\'' +
//                ", timeSlot='" + timeSlot + '\'' +
//                ", location='" + location + '\'' +
//                ", reportedBy='" + reportedBy + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
//                '}';
//    }
}
