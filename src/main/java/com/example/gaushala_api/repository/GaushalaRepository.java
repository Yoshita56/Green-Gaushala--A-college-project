
package com.example.gaushala_api.repository;

import com.example.gaushala_api.model.Gaushala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface GaushalaRepository extends JpaRepository<Gaushala, Long> {
    Gaushala findByUserId(String userId);

}
