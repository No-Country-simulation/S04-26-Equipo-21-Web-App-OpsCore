package com.opscore.incident.service;

import com.opscore.incident.model.IncidentRca;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.repository.IncidentRcaRepository;
import com.opscore.incident.repository.IncidenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RcaService {

    private final IncidentRcaRepository rcaRepository;
    private final IncidenteRepository incidenteRepository;

    @Transactional
    public void registrarRca(Long incidenteId, IncidentRca rca) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        rca.setIncidente(incidente);
        rca.setFechaAnalisis(LocalDateTime.now());

        rcaRepository.save(rca);
    }

    public IncidentRca obtenerRca(Long incidenteId) {
        return rcaRepository.findByIncidenteId(incidenteId)
                .orElseThrow(() -> new RuntimeException("RCA no encontrado"));
    }
}
