package com.example.gaushala_api.repository;
import com.example.gaushala_api.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportRepository extends JpaRepository<Report, Long> {
}
