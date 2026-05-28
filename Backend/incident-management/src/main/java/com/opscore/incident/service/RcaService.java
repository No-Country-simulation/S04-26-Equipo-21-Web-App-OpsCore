package com.opscore.incident.service;

<<<<<<< HEAD
import com.opscore.incident.dto.RCARequestDTO; // 👈 Usamos tu DTO de entrada
=======
>>>>>>> upstream/develop
import com.opscore.incident.model.IncidentRca;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.repository.IncidentRcaRepository;
import com.opscore.incident.repository.IncidenteRepository;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
=======
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
>>>>>>> upstream/develop

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RcaService {

    private final IncidentRcaRepository rcaRepository;
    private final IncidenteRepository incidenteRepository;

    @Transactional
<<<<<<< HEAD
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
=======
    public void registrarRca(Long incidenteId, IncidentRca rca) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        rca.setIncidente(incidente);
        rca.setFechaAnalisis(LocalDateTime.now());

        rcaRepository.save(rca);
>>>>>>> upstream/develop
    }

    public IncidentRca obtenerRca(Long incidenteId) {
        return rcaRepository.findByIncidenteId(incidenteId)
<<<<<<< HEAD
                .orElseThrow(() -> new RuntimeException("Análisis RCA no encontrado para el incidente con ID: " + incidenteId));
    }
}
=======
                .orElseThrow(() -> new RuntimeException("RCA no encontrado"));
    }
}
>>>>>>> upstream/develop
