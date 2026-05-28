package com.opscore.incident.mapper;

import com.opscore.incident.dto.IncidenteRequestDTO;
import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.EstadoValidacion;
import com.opscore.incident.enums.Prioridad;
import com.opscore.incident.enums.TipoFalla;
import com.opscore.incident.model.Incidente;
import org.springframework.stereotype.Component;

@Component
public class IncidenteMapper {

    // Entrada: JSON de Postman -> Entidad de Base de Datos
    public Incidente toEntity(IncidenteRequestDTO dto) {
        if (dto == null) return null;

        return Incidente.builder()
                .titulo("Incidente en " + dto.getMachine()) // Título automático para la demo
                .descripcion(dto.getDescription())
                .prioridad(Prioridad.valueOf(dto.getSeverity().toUpperCase()))
                .tipoFalla(TipoFalla.valueOf(dto.getIncidentType().toUpperCase()))
                .estadoOperativo(EstadoOperativo.ABIERTO)
                .estadoValidacion(EstadoValidacion.PENDIENTE)
                .build();
    }

    // Salida: Entidad Base de Datos -> JSON de Respuesta para Postman/Front
    public IncidenteResponseDTO toDTO(Incidente incidente) {
        if (incidente == null) return null;

        return IncidenteResponseDTO.builder()
                .id(incidente.getId())
                .titulo(incidente.getTitulo())
                .descripcion(incidente.getDescripcion())
                .prioridad(incidente.getPrioridad())
                .tipoFalla(incidente.getTipoFalla())
                .estadoOperativo(incidente.getEstadoOperativo())
                .estadoValidacion(incidente.getEstadoValidacion())
                .area(incidente.getArea() != null ? incidente.getArea().getNombre() : "No asignada")
                .estacion(incidente.getEstacion() != null ? incidente.getEstacion().getNombre() : "No asignada")
                .tecnico(incidente.getTecnico() != null ? incidente.getTecnico().getNombre() : "No asignado")
                .fechaCreacion(incidente.getCreatedAt()) // Mapeado de BaseEntity
                .fechaAsignacion(incidente.getFechaAsignacion())
                .fechaResolucion(incidente.getFechaResolucion())
                .build();
    }
}