package com.opscore.incident.repository;

import com.opscore.incident.model.IncidentRca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncidentRcaRepository extends JpaRepository<IncidentRca, Long> {

    Optional<IncidentRca> findByIncidenteId(Long incidenteId);
}
