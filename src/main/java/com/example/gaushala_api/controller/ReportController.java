package com.example.gaushala_api.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    // Retrieve all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    // Retrieve a specific report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Report with ID: " + id + " not found.");
                    return ResponseEntity.notFound().build();
                });
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
            report.setStatus("pending"); // Initialize status as pending

            Report savedReport = reportService.saveReport(report);
            logger.info("Report created successfully: " + savedReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
        } catch (IOException e) {
            logger.error("Error while uploading image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle errors
        }
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            Report updatedReport = existingReport.get();

            // Update the fields with values from the request body if provided
            if (report.getArea() != null) updatedReport.setArea(report.getArea());
            if (report.getTimeSlot() != null) updatedReport.setTimeSlot(report.getTimeSlot());
            if (report.getLocation() != null) updatedReport.setLocation(report.getLocation());
            if (report.getReportedBy() != null) updatedReport.setReportedBy(report.getReportedBy());
            if (report.getImage() != null) updatedReport.setImage(report.getImage());
            if (report.getStatus() != null) updatedReport.setStatus(report.getStatus());

            Report savedReport = reportService.saveReport(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            logger.warn("Report with ID: " + id + " not found for update.");
            return ResponseEntity.notFound().build();
        }
    }

    // Accept a report request
    @PutMapping("/{id}/accept")
    public ResponseEntity<Report> acceptReport(@PathVariable Long id, @RequestParam String acceptedBy) {
        logger.info("Attempting to accept report with ID: " + id + " by user: " + acceptedBy);

        // Delegate the logic to the service layer
        Optional<Report> acceptedReport = reportService.acceptReport(id, acceptedBy);

        if (acceptedReport.isPresent()) {
            logger.info("Report accepted: " + acceptedReport.get() + " by user: " + acceptedBy);
            return ResponseEntity.ok(acceptedReport.get());
        } else {
            logger.warn("Report with ID: " + id + " not found for acceptance.");
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            reportService.deleteReport(id);
            logger.info("Report with ID: " + id + " deleted successfully.");
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            logger.warn("Report with ID: " + id + " not found for deletion.");
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }

    // Update the status of an existing report (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Report> updateReportStatus(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            Report updatedReport = existingReport.get();

            // Update only the status if provided
            if (report.getStatus() != null) {
                updatedReport.setStatus(report.getStatus());
            }

            // Save the updated report
            Report savedReport = reportService.saveReport(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            logger.warn("Report with ID: " + id + " not found for status update.");
            return ResponseEntity.notFound().build();
        }
    }
}
