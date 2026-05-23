package com.opscore.incident.service;


import com.opscore.incident.dto.AreaMetricDTO;
import com.opscore.incident.dto.DashboardMetricDTO;
import com.opscore.incident.dto.MachineMetricDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.repository.DashboardMetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MetricaService {

    private final DashboardMetricsRepository repository;
    Map<EstadoOperativo, Long> estados =
            repository.countByEstado().stream()
                    .collect(Collectors.toMap(
                            o -> (EstadoOperativo) o[0],
                            o -> (Long) o[1]
                    ));

    public DashboardMetricDTO getDashboard() {

        return DashboardMetricDTO.builder()
                .incidentesAbiertos(estados.getOrDefault(EstadoOperativo.ABIERTO, 0L))
                .incidentesAsignados(estados.getOrDefault(EstadoOperativo.ASIGNADO, 0L))
                .incidentesEnProceso(estados.getOrDefault(EstadoOperativo.EN_PROCESO, 0L))
                .incidentesResueltos(estados.getOrDefault(EstadoOperativo.RESUELTO, 0L))
                .incidentesValidados(estados.getOrDefault(EstadoOperativo.VALIDADO, 0L))
                .incidentesCerrados(estados.getOrDefault(EstadoOperativo.CERRADO, 0L))
                .mttr(repository.calcularMTTR())
                .mtta(repository.calcularMTTA())
                .incidentesCriticosFueraSla(repository.incidentesCriticosFueraSla())
                .incidentesPorArea(
                        repository.incidentesPorArea()
                                .stream()
                                .map(o -> new AreaMetricDTO(
                                        (String) o[0],
                                        (Long) o[1]
                                ))
                                .toList()
                )
                .maquinasConMasFallas(
                        repository.topMaquinasConFallas()
                                .stream()
                                .map(o -> new MachineMetricDTO(
                                        (String) o[0],
                                        (Long) o[1]
                                ))
                                .toList()
                )
                .build();
    }
}
