package com.opscore.incident.mapper;


import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.model.Incidente;
import org.springframework.stereotype.Component;

@Component
public class IncidenteMapper {

    public IncidenteResponseDTO toDTO(Incidente incidente) {
        if (incidente == null) return null;

        return IncidenteResponseDTO.builder()
                .id(incidente.getId())
                .descripcion(incidente.getDescripcion())
                .estado(incidente.getEstado())
                .prioridad(incidente.getPrioridad())
                .nombreEstacion(incidente.getEstacion().getNombre())
                .nombreTecnico(incidente.getTecnico() != null ? incidente.getTecnico().getNombre() : "No asignado")
                .build();
    }
}
