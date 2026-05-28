package com.opscore.incident.dto;

import lombok.Builder;
import lombok.Data;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> upstream/develop
@Data
@Builder
public class DashboardMetricDTO {

<<<<<<< HEAD
    // ESTADOS OPERATIVOS
    private long incidentesAbiertos;

    private long incidentesAsignados;

=======
    private long incidentesAbiertos;

>>>>>>> upstream/develop
    private long incidentesEnProceso;

    private long incidentesResueltos;

    private long incidentesValidados;

<<<<<<< HEAD
    private long incidentesCerrados;

    // OPERACIÓN
=======
>>>>>>> upstream/develop
    private long incidentesSinAsignar;

    private long incidentesCriticos;

<<<<<<< HEAD
    // KPI INDUSTRIALES
    private double mttr; // Mean Time To Repair

    private double mtta; // Mean Time To Assign

    // SLA
    private long incidentesCriticosFueraSla;

    // ANALÍTICA
    private List<AreaMetricDTO> incidentesPorArea;

    private List<MachineMetricDTO> maquinasConMasFallas;
=======
    private double mttr;
>>>>>>> upstream/develop
}
