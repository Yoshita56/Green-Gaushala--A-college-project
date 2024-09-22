package com.example.gaushala_api.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Retrieve all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports); // Use ResponseEntity for consistency
    }

    // Retrieve a specific report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new report with image upload
    @PostMapping
    public ResponseEntity<Report> createReport(
            @RequestParam("area") String area,
            @RequestParam("timeSlot") String timeSlot,
            @RequestParam("location") String location,
            @RequestParam("reportedBy") String reportedBy,
            @RequestParam("image") MultipartFile imageFile) { // Accept the image file

        try {
            byte[] imageBytes = imageFile.getBytes(); // Convert the image to byte array

            Report report = new Report();
            report.setArea(area);
            report.setTimeSlot(timeSlot);
            report.setLocation(location);
            report.setReportedBy(reportedBy);
            report.setImage(imageBytes); // Set the image bytes

            Report savedReport = reportService.saveReport(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle errors
        }
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            report.setId(id);
            Report updatedReport = reportService.saveReport(report);
            return ResponseEntity.ok(updatedReport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }
}
