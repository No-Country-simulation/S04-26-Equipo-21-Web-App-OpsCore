package com.opscore.incident.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupervisorDashboardDTO {

    private long incidentesAbiertos;

    private long incidentesCriticos;

    private long incidentesSinAsignar;

    private long tecnicosDisponibles;

    private Double mttr;
<<<<<<< HEAD
}
=======
}
>>>>>>> upstream/develop
