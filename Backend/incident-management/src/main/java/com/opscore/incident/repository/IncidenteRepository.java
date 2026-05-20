package com.opscore.incident.repository;

import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.TipoFalla;
import com.opscore.incident.model.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    List<Incidente> findByEstadoOperativo(EstadoOperativo estado);

    List<Incidente> findByTipoFalla(TipoFalla tipoFalla);

    List<Incidente> findByEstacionId(Long estacionId);

    List<Incidente> findByTecnicoId(Long tecnicoId);
}
