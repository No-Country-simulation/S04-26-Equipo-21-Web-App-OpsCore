package com.opscore.incident.service;

import com.opscore.incident.dto.AreaResponseDTO;
import com.opscore.incident.dto.EstacionTrabajoResponseDTO;
import com.opscore.incident.exception.AreaNoEncontradaException;
import com.opscore.incident.mapper.AreaMapper;
import com.opscore.incident.repository.AreaRepository;
import com.opscore.incident.repository.EstacionTrabajoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaService {

    private final AreaRepository areaRepository;
    private final EstacionTrabajoRepository estacionTrabajoRepository;
    private final AreaMapper areaMapper;

    public List<AreaResponseDTO> listarAreas() {
        return areaMapper.toAreaDtoList(areaRepository.findAll());
    }

    public List<EstacionTrabajoResponseDTO> listarEstacionesPorArea(Long areaId) {
        areaRepository.findById(areaId)
                .orElseThrow(() -> new AreaNoEncontradaException(areaId));
        return areaMapper.toEstacionDtoList(estacionTrabajoRepository.findByAreaId(areaId));
    }
}
