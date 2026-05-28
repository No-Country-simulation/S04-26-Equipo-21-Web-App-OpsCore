package com.opscore.incident.service;

import com.opscore.incident.dto.RCARequestDTO; // 👈 Usamos tu DTO de entrada
import com.opscore.incident.model.IncidentRca;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.repository.IncidentRcaRepository;
import com.opscore.incident.repository.IncidenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RcaService {

    private final IncidentRcaRepository rcaRepository;
    private final IncidenteRepository incidenteRepository;

    @Transactional
    public IncidentRca registrarRca(RCARequestDTO dto) {

        Incidente incidente = incidenteRepository.findById(dto.getIncidenteId())
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        // Mapeamos los datos del DTO a la entidad de persistencia text-based
        IncidentRca rca = IncidentRca.builder()
                .incidente(incidente)
                .porque1(dto.getPorque1())
                .porque2(dto.getPorque2())
                .porque3(dto.getPorque3())
                .porque4(dto.getPorque4())
                .porque5(dto.getPorque5())
                .causaRaiz(dto.getCausaRaiz())
                .accionCorrectiva(dto.getAccionCorrectiva())
                .fechaAnalisis(LocalDateTime.now())
                .build();

        return rcaRepository.save(rca);
    }

    public IncidentRca obtenerRca(Long incidenteId) {
        return rcaRepository.findByIncidenteId(incidenteId)
                .orElseThrow(() -> new RuntimeException("Análisis RCA no encontrado para el incidente con ID: " + incidenteId));
    }
}