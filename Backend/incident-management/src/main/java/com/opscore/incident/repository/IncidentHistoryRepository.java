package com.opscore.incident.repository;

import com.opscore.incident.model.IncidentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentHistoryRepository extends JpaRepository<IncidentHistory, Long> {

    List<IncidentHistory> findByIncidenteIdOrderByTimestampAsc(Long incidenteId);
}
