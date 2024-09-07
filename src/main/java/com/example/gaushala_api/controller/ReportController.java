package com.example.gaushala_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.service.ReportService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.saveReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> existingReport = reportService.getReportById(id);
        return existingReport.map(r -> {
            report.setId(id);
            Report updatedReport = reportService.saveReport(report);
            return ResponseEntity.ok(updatedReport);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Optional<Report> existingReport = reportService.getReportById(id);
        if (existingReport.isPresent()) {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 Not Found
        }
    }
}
