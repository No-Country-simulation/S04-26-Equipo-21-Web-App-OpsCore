package com.opscore.incident.repository;

import com.opscore.incident.model.Incidente;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
    List<Incidente> findByEstado(EstadoIncidente estado);
    List<Incidente> findByPrioridad(Prioridad prioridad);
    List<Incidente> findByEstacionId(Long estacionId);

    // Filtra incidentes por el ID del área vinculada a la estación
    List<Incidente> findByEstacionAreaId(Long areaId);
}