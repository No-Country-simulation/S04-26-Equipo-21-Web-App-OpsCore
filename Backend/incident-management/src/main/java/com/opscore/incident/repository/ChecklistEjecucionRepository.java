package com.opscore.incident.repository;

import com.opscore.incident.model.ChecklistEjecucion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChecklistEjecucionRepository extends JpaRepository<ChecklistEjecucion, Long> {
    // Corregido: Ahora usa 'CreatedAt' de BaseEntity en lugar de 'FechaRealizacion'
    List<ChecklistEjecucion> findByEstacionIdOrderByCreatedAtDesc(Long estacionId);
}
