package com.opscore.incident.mapper;

import com.opscore.incident.dto.ChecklistRequestDTO;
import com.opscore.incident.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChecklistMapper {

    public ChecklistEjecucion toEntity(ChecklistRequestDTO dto, Checklist plantilla, EstacionTrabajo estacion, Usuario operador) {
        ChecklistEjecucion ejecucion = ChecklistEjecucion.builder()
                .plantilla(plantilla)
                .estacion(estacion)
                .operador(operador)
                .build();

        // Convertimos la lista de respuestas del DTO a Entidades
        List<RespuestaPuntoControl> respuestas = dto.getRespuestas().stream()
                .map(r -> RespuestaPuntoControl.builder()
                        .completado(r.isCompletado())
                        .observaciones(r.getObservaciones())
                        .ejecucion(ejecucion)
                        .build())
                .collect(Collectors.toList());

        ejecucion.setRespuestas(respuestas);
        return ejecucion;
    }
}