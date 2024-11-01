package com.example.gaushala_api.service;

import com.example.gaushala_api.model.Report;
import com.example.gaushala_api.model.Gaushala; // Import Gaushala model
import com.example.gaushala_api.repository.GaushalaRepository;
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
        return reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    @Autowired
    private GaushalaRepository gaushalaRepository;

    public Optional<Gaushala> getGaushalaById(Long gaushalaId) {
        return gaushalaRepository.findById(gaushalaId); // Assuming this returns Optional<Gaushala>
    }


    // Updated method to accept a report and set the acceptedBy field
    // In your ReportService class
    public Optional<Report> acceptReport(Long reportId, Gaushala gaushala) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            report.setAcceptedBy(gaushala); // Assign the accepting Gaushala entity
            report.setStatus("accepted"); // Optionally update the status to 'accepted'
            return Optional.of(reportRepository.save(report)); // Save and return the updated report
        }
        return Optional.empty();
    }



    // New method to find reports accepted by a specific Gaushala
    public List<Report> getReportsAcceptedByGaushala(Gaushala acceptedBy) {
        return reportRepository.findByAcceptedBy(acceptedBy);
    }
}
