package com.opscore.incident.repository;

import com.opscore.incident.model.AnalisisCausaRaiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalisisCausaRaizRepository extends JpaRepository<AnalisisCausaRaiz, Long> {

    List<AnalisisCausaRaiz> findByIncidenteEstacionId(Long estacionId);
}
