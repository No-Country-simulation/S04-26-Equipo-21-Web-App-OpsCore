package com.opscore.incident.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardMetricDTO {

    // ESTADOS OPERATIVOS
    private long incidentesAbiertos;

    private long incidentesAsignados;

    private long incidentesEnProceso;

    private long incidentesResueltos;

    private long incidentesValidados;

    private long incidentesCerrados;

    // OPERACIÓN
    private long incidentesSinAsignar;

    private long incidentesCriticos;

    // KPI INDUSTRIALES
    private double mttr; // Mean Time To Repair

    private double mtta; // Mean Time To Assign

    // SLA
    private long incidentesCriticosFueraSla;

    // ANALÍTICA
    private List<AreaMetricDTO> incidentesPorArea;

    private List<MachineMetricDTO> maquinasConMasFallas;
}
