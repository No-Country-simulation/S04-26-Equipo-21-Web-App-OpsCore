package com.opscore.incident.mapper;

import com.opscore.incident.dto.AreaResponseDTO;
import com.opscore.incident.dto.EstacionTrabajoResponseDTO;
import com.opscore.incident.model.Area;
import com.opscore.incident.model.EstacionTrabajo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaMapper {

    public AreaResponseDTO toAreaDto(Area area) {
        return new AreaResponseDTO(area.getId(), area.getNombre());
    }

    public EstacionTrabajoResponseDTO toEstacionDto(EstacionTrabajo estacion) {
        return new EstacionTrabajoResponseDTO(estacion.getId(), estacion.getNombre(), estacion.getCodigo());
    }

    public List<AreaResponseDTO> toAreaDtoList(List<Area> areas) {
        return areas.stream().map(this::toAreaDto).toList();
    }

    public List<EstacionTrabajoResponseDTO> toEstacionDtoList(List<EstacionTrabajo> estaciones) {
        return estaciones.stream().map(this::toEstacionDto).toList();
    }
}
