package com.opscore.incident.dto;

import lombok.Data;

@Data
public class RCARequestDTO {

    private Long incidenteId;

    private String porque1;
    private String porque2;
    private String porque3;
    private String porque4;
    private String porque5;

    private String causaRaiz;

    private String accionCorrectiva;
}
