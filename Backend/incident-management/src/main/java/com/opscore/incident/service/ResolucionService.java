package com.opscore.incident.service;

import com.opscore.incident.dto.ResolucionRequestDTO;
import com.opscore.incident.dto.ResolucionResponseDTO;
import com.opscore.incident.model.*;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResolucionService {

    private final ResolucionRepository resolucionRepository;
    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ResolucionResponseDTO finalizarReparacion(ResolucionRequestDTO dto) {
        // 1. Validar existencia del incidente
        Incidente incidente = incidenteRepository.findById(dto.getIncidenteId())
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado con ID: " + dto.getIncidenteId()));

        // 2. Cambiar estado del incidente
        incidente.setEstado(EstadoIncidente.RESUELTO);

        // 3. Liberar al técnico asignado
        Usuario tecnico = incidente.getTecnico();
        if (tecnico != null) {
            tecnico.setDisponible(true);
            usuarioRepository.save(tecnico);
        }

        // 4. Crear y guardar la entidad Resolucion
        Resolucion nuevaResolucion = Resolucion.builder()
                .incidente(incidente)
                .descripcionSolucion(dto.getComentario())
                .build();

        Resolucion guardada = resolucionRepository.save(nuevaResolucion);
        incidenteRepository.save(incidente);

        // 5. Retornar el DTO de respuesta
        return ResolucionResponseDTO.builder()
                .id(guardada.getId())
                .incidenteId(incidente.getId())
                .comentario(guardada.getDescripcionSolucion())
                .fechaResolucion(LocalDateTime.now()) // O usar la de BaseEntity si aplica
                .build();
    }
}