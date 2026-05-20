package com.opscore.incident.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardMetricDTO {

    private long incidentesAbiertos;

    private long incidentesEnProceso;

    private long incidentesResueltos;

    private long incidentesValidados;

    private long incidentesSinAsignar;

    private long incidentesCriticos;

    private double mttr;
}
