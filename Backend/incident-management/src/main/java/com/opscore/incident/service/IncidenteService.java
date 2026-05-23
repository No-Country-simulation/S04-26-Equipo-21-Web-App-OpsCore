package com.opscore.incident.service;

import com.opscore.incident.dto.IncidenteReportRequestDTO;
import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.TipoFalla;
import com.opscore.incident.mapper.IncidenteMapper;
import com.opscore.incident.model.*;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstacionTrabajoRepository estacionRepository;
    private final NotificationService notificationService;
    private final IncidenteMapper incidenteMapper;

    // CREAR INCIDENTE
    @Transactional
    public IncidenteResponseDTO crearIncidente(IncidenteReportRequestDTO dto) {

        // 1. Validar estación
        EstacionTrabajo estacion = estacionRepository.findById(dto.getEstacionId())
                .orElseThrow(() -> new RuntimeException("Estación no encontrada"));

        Area area = estacion.getArea();

        // 2. Crear título automático (clave para gerencia)
        String titulo = generarTitulo(area.getNombre(), estacion.getNombre(), dto.getTipoFalla());

        // 3. Construir incidente
        Incidente incidente = Incidente.builder()
                .titulo(titulo)
                .descripcion(dto.getDescripcion())
                .prioridad(dto.getPrioridad())
                .tipoFalla(dto.getTipoFalla())
                .estadoOperativo(EstadoOperativo.ABIERTO)
                .estadoValidacion(com.opscore.incident.enums.EstadoValidacion.PENDIENTE)
                .area(area)
                .estacion(estacion)
                .build();

        // 4. Guardar primero (evita inconsistencias)
        Incidente guardado = incidenteRepository.save(incidente);

        // 5. Intentar asignación automática
        asignarTecnicoSiDisponible(guardado);

        return incidenteMapper.toDTO(guardado);
    }

    private Long obtenerEspecialidadPorTipoFalla(TipoFalla tipoFalla) {

        return switch (tipoFalla) {
            case ELECTRICA -> 1L;
            case MECANICA -> 2L;
            case HIDRAULICA -> 3L;
            default -> 4L;
        };
    }

    // ASIGNACIÓN AUTOMÁTICA
    private void asignarTecnicoSiDisponible(Incidente incidente) {

        List<Usuario> tecnicos = usuarioRepository.findTecnicoAsignable(
                incidente.getArea().getId(),
                obtenerEspecialidadPorTipoFalla(incidente.getTipoFalla())
        );

        if (tecnicos.isEmpty()) {
            // queda en cola implícita
            notificationService.notificarIncidenteGeneral(
                    "Incidente sin técnico disponible: " + incidente.getTitulo()
            );
            return;
        }

        // elegir técnico menos cargado
        Usuario tecnico = tecnicos.stream()
                .min(Comparator.comparing(t -> contarIncidentesActivos(t.getId())))
                .orElse(tecnicos.get(0));

        // asignar
        incidente.setTecnico(tecnico);
        incidente.setEstadoOperativo(EstadoOperativo.ASIGNADO);
        incidente.setFechaAsignacion(LocalDateTime.now());

        tecnico.setDisponible(false);
        usuarioRepository.save(tecnico);
        incidenteRepository.save(incidente);

        // notificación
        notificationService.enviarNotificacionAsignacion(
                tecnico.getNumeroReloj(),
                "Nuevo incidente asignado: " + incidente.getTitulo()
        );
    }

    // UTIL: TÍTULO AUTOMÁTICO
    private String generarTitulo(String area, String estacion, TipoFalla tipoFalla) {
        return "Falla " + tipoFalla +
                " en " + estacion +
                " - " + area;
    }

    // UTIL: CARGA TÉCNICO
    private long contarIncidentesActivos(Long tecnicoId) {
        return incidenteRepository.findByTecnicoId(tecnicoId)
                .stream()
                .filter(i -> i.getEstadoOperativo() != EstadoOperativo.RESUELTO)
                .count();
    }
}