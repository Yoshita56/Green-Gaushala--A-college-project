package com.example.gaushala_api.service;

import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report saveReport(Report report) {
        // Optionally, you can add validation logic here if needed
        return reportRepository.save(report); // This will save the report with the byte array image
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    // New method to accept the report and set the acceptedBy field
    public Optional<Report> acceptReport(Long id, String acceptedBy) {
        Optional<Report> existingReport = reportRepository.findById(id);
        if (existingReport.isPresent()) {
            Report report = existingReport.get();
            report.setStatus("accepted");  // Update the status
            report.setAcceptedBy(acceptedBy); // Set the acceptedBy field
            return Optional.of(reportRepository.save(report)); // Save the updated report
        }
        return Optional.empty(); // Return empty if report not found
    }


}
