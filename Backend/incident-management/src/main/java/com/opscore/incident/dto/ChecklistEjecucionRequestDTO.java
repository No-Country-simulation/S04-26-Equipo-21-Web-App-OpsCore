package com.opscore.incident.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ChecklistEjecucionRequestDTO {
    private Long plantillaId;
    private Long estacionId;
    private Long operadorId;
    private List<RespuestaPuntoControlDTO> respuestas;

    @Getter @Setter
    public static class RespuestaPuntoControlDTO {
        private Long itemId;
        private boolean completado;
        private String observaciones;
    }
}
