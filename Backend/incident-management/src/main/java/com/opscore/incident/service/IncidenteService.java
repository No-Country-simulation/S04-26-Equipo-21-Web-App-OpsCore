package com.opscore.incident.service;

import com.opscore.incident.dto.IncidenteReportRequestDTO;
import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.mapper.IncidenteMapper;
import com.opscore.incident.model.*;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AreaRepository areaRepository;
    private final EstacionTrabajoRepository estacionRepository;
    private final NotificationService notificationService;
    private final IncidenteMapper incidenteMapper;

    @Transactional
    public IncidenteResponseDTO registrarIncidenteEnTiempoReal(IncidenteReportRequestDTO dto) {
        // 1. Validar e hidratar las relaciones desde la base de datos
        Area area = areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        EstacionTrabajo deTrabajo = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new RuntimeException("Estación no encontrada"));

        Usuario operador = usuarioRepository.findById(dto.getOperadorId())
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        // 2. Construir la entidad Incidente (BaseEntity asignará el createdAt automáticamente)
        Incidente nuevoIncidente = Incidente.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .prioridad(dto.getPrioridad())
                .estado(EstadoIncidente.ABIERTO) // Todo incidente inicia ABIERTO
                .area(area)
                .estacion(deTrabajo)
                .operador(operador)
                .build();

        // 3. Motor de Asignación Automática en Tiempo Real
        List<Usuario> tecnicosAptos = usuarioRepository.findTecnicoAsignable(
                area.getId(),
                dto.getEspecialidadId()
        );

        if (!tecnicosAptos.isEmpty()) {
            Usuario tecnicoAsignado = tecnicosAptos.get(0);
            nuevoIncidente.setTecnico(tecnicoAsignado);
            nuevoIncidente.setEstado(EstadoIncidente.EN_PROCESO);

            // Bloquear disponibilidad del técnico
            tecnicoAsignado.setDisponible(false);
            usuarioRepository.save(tecnicoAsignado);

            // Notificar de inmediato al técnico por WebSocket
            notificationService.enviarNotificacionAsignacion(
                    tecnicoAsignado.getNumeroReloj(),
                    "¡Alerta inmediata! Incidente asignado: " + nuevoIncidente.getTitulo()
            );
        } else {
            // Si no hay técnicos, se queda ABIERTO y notificamos al canal general de supervisores
            notificationService.notificarIncidenteGeneral(
                    "Incidente sin técnico disponible: " + nuevoIncidente.getTitulo()
            );
        }

        // 4. Persistir en la base de datos y retornar respuesta limpia
        Incidente guardado = incidenteRepository.save(nuevoIncidente);
        return incidenteMapper.toDTO(guardado);
    }
}
