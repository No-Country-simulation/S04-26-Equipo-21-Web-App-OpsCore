package com.opscore.incident.repository;

import com.opscore.incident.model.RespuestaPuntoControl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespuestaPuntoControlRepository extends JpaRepository<RespuestaPuntoControl, Long> {
    // Busca puntos que NO fueron completados (fallos)
    List<RespuestaPuntoControl> findByCompletadoFalse();
}