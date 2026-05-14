package com.opscore.incident.service;

import com.opscore.incident.model.Incidente;
import com.opscore.incident.repository.IncidenteRepository;
import com.opscore.incident.enums.EstadoIncidente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetricaService {

    private final IncidenteRepository incidenteRepository;

    public Map<EstadoIncidente, Long> obtenerConteoPorEstado() {
        // Obtenemos todos los incidentes y los agrupamos por su estado
        return incidenteRepository.findAll().stream()
                .collect(Collectors.groupingBy(Incidente::getEstado, Collectors.counting()));
    }

    public Map<String, Long> obtenerIncidentesPorArea() {
        // Agrupamos incidentes por el nombre del área de la estación
        return incidenteRepository.findAll().stream()
                .collect(Collectors.groupingBy(i -> i.getEstacion().getArea().getNombre(), Collectors.counting()));
    }
}
