package com.opscore.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AreaMetricDTO {

    private String area;

    private Long totalIncidentes;
}
