package com.opscore.incident.repository;

import com.opscore.incident.model.EstacionTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstacionTrabajoRepository extends JpaRepository<EstacionTrabajo, Long> {
    List<EstacionTrabajo> findByAreaId(Long areaId);
    Optional<EstacionTrabajo> findByCodigo(String codigo);
}
