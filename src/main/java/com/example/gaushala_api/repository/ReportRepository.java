package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // Method to find reports by their status
    List<Report> findByStatus(String status);

    // Method to find reports accepted by a specific gaushala user
    List<Report> findByAcceptedBy(String acceptedBy);

    // Optional: Custom query to find all reports with a specific status
    @Query("SELECT r FROM Report r WHERE r.status = :status")
    List<Report> getReportsByStatus(@Param("status") String status);
}
