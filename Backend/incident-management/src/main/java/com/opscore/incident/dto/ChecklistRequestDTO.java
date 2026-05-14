package com.opscore.incident.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChecklistRequestDTO {
    private Long plantillaId;
    private Long estacionId;
    private Long operadorId;
    private List<RespuestaPuntoDTO> respuestas;

    @Data
    public static class RespuestaPuntoDTO {
        private Long itemId;
        private boolean completado;
        private String observaciones;
    }
}