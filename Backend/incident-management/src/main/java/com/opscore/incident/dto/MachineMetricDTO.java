package com.opscore.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineMetricDTO {

    private String estacion;

    private Long totalIncidentes;
}
