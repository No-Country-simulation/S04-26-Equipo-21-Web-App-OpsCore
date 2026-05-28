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

    public DashboardMetricDTO getDashboard() {

        Map<EstadoOperativo, Long> estados =
                repository.countByEstado().stream()
                        .collect(Collectors.toMap(
                                o -> (EstadoOperativo) o[0],
                                o -> (Long) o[1]
                        ));

        return DashboardMetricDTO.builder()
                .incidentesAbiertos(estados.getOrDefault(EstadoOperativo.ABIERTO, 0L))
                .incidentesEnProceso(estados.getOrDefault(EstadoOperativo.EN_PROCESO, 0L))
                .incidentesResueltos(estados.getOrDefault(EstadoOperativo.RESUELTO, 0L))
                .incidentesValidados(estados.getOrDefault(EstadoOperativo.VALIDADO, 0L))
                .mttr(repository.calcularMTTR())
                .build();
    }


}
