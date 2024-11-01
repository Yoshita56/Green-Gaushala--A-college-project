package com.example.gaushala_api.controller;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.model.Gaushala;
import com.example.gaushala_api.service.ReportService;
import com.example.gaushala_api.service.GaushalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private GaushalaService gaushalaService;

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
                    logger.warn("Report with ID: {} not found.", id);
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
            @RequestParam("image") MultipartFile imageFile) {
        try {
            byte[] imageBytes = imageFile.getBytes();

            Report report = new Report();
            report.setArea(area);
            report.setTimeSlot(timeSlot);
            report.setLocation(location);
            report.setReportedBy(reportedBy);
            report.setImage(imageBytes);
            report.setStatus("pending");

            Report savedReport = reportService.saveReport(report);
            logger.info("Report created successfully: {}", savedReport);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
        } catch (IOException e) {
            logger.error("Error while uploading image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            Report updatedReport = existingReport.get();

            // Update fields if they are not null
            if (report.getArea() != null) updatedReport.setArea(report.getArea());
            if (report.getTimeSlot() != null) updatedReport.setTimeSlot(report.getTimeSlot());
            if (report.getLocation() != null) updatedReport.setLocation(report.getLocation());
            if (report.getReportedBy() != null) updatedReport.setReportedBy(report.getReportedBy());
            if (report.getImage() != null) updatedReport.setImage(report.getImage());
            if (report.getStatus() != null) updatedReport.setStatus(report.getStatus());

            Report savedReport = reportService.saveReport(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            logger.warn("Report with ID: {} not found for update.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Accept a report request
    @PutMapping("/{id}/accept")
    public ResponseEntity<Report> acceptReport(@PathVariable Long id, @RequestParam Long gaushalaId) {
        Gaushala gaushala = gaushalaService.getGaushalaById(gaushalaId);
        if (gaushala != null) { // Check if gaushala is found
            Optional<Report> acceptedReport = reportService.acceptReport(id, gaushala);
            return acceptedReport
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }






    // Delete a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportService.getReportById(id).isPresent()) {
            reportService.deleteReport(id);
            logger.info("Report with ID: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Report with ID: {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Update the status of an existing report (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Report> updateReportStatus(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            Report updatedReport = existingReport.get();
            if (report.getStatus() != null) {
                updatedReport.setStatus(report.getStatus());
            }
            Report savedReport = reportService.saveReport(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            logger.warn("Report with ID: {} not found for status update.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Retrieve a specific Gaushala by ID
    @GetMapping("/gaushalas/{id}")
    public ResponseEntity<Gaushala> getGaushalaById(@PathVariable Long id) {
        Gaushala gaushala = gaushalaService.getGaushalaById(id);
        if (gaushala != null) {
            return ResponseEntity.ok(gaushala);
        } else {
            logger.warn("Gaushala with ID: {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }
}
