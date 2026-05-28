package com.opscore.incident.service;

import com.opscore.incident.dto.IncidenteRequestDTO; // 👈 Cambiado al DTO del Front
import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.EstadoValidacion;
import com.opscore.incident.enums.EventType;
import com.opscore.incident.enums.Prioridad;
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
    private final IncidentHistoryService historyService; // 👈 Añadido para trazabilidad
    private final IncidenteMapper incidenteMapper;

    // CREAR INCIDENTE DESDE EL FRONT/POSTMAN (MVP DEMO)
    @Transactional
    public IncidenteResponseDTO crearIncidente(IncidenteRequestDTO dto) {

        // 1. Validar estación de trabajo por el Código String (ej: "ST-01")
        EstacionTrabajo estacion = estacionRepository.findByCodigo(dto.getMachine())
                .orElseThrow(() -> new RuntimeException("Estación con código '" + dto.getMachine() + "' no encontrada"));

        Area area = estacion.getArea();

        // 2. Mapear Enums dinámicamente desde el texto del JSON
        Prioridad prioridad = Prioridad.valueOf(dto.getSeverity().toUpperCase());
        TipoFalla tipoFalla = TipoFalla.valueOf(dto.getIncidentType().toUpperCase());

        // 3. Crear título automático para trazabilidad gerencial
        String titulo = "Falla " + tipoFalla + " en " + estacion.getNombre() + " - " + area.getNombre();

        // 4. Construir incidente base
        Incidente incidente = Incidente.builder()
                .titulo(titulo)
                .descripcion(dto.getDescription())
                .prioridad(prioridad)
                .tipoFalla(tipoFalla)
                .estadoOperativo(EstadoOperativo.ABIERTO)
                .estadoValidacion(EstadoValidacion.PENDIENTE)
                .area(area)
                .estacion(estacion)
                .build();

        // 5. Guardar incidente inicial
        Incidente guardado = incidenteRepository.save(incidente);

        // 6. Registrar evento en el historial de trazabilidad
        historyService.logEvent(
                guardado,
                EventType.INCIDENT_CREATED,
                "Incidente reportado por usuario base. Checklist de seguridad validado.",
                null, "OPERADOR",
                null, EstadoOperativo.ABIERTO
        );

        // 7. Notificación vía WebSocket a supervisores y técnicos
        notificationService.notificarIncidenteGeneral("NUEVO INCIDENTE: " + titulo);

        // 8. Intentar asignación automática por especialidad
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

    private void asignarTecnicoSiDisponible(Incidente incidente) {
        List<Usuario> tecnicos = usuarioRepository.findTecnicoAsignable(
                incidente.getArea().getId(),
                obtenerEspecialidadPorTipoFalla(incidente.getTipoFalla())
        );

        if (tecnicos.isEmpty()) {
            notificationService.notificarIncidenteGeneral("Incidente en cola (sin técnico disponible): " + incidente.getTitulo());
            return;
        }

        // Elegir al técnico conectado con menos carga de trabajo activa
        Usuario tecnico = tecnicos.stream()
                .min(Comparator.comparing(t -> contarIncidentesActivos(t.getId())))
                .orElse(tecnicos.get(0));

        // Actualizar asignación
        incidente.setTecnico(tecnico);
        incidente.setEstadoOperativo(EstadoOperativo.ASIGNADO);
        incidente.setFechaAsignacion(LocalDateTime.now());

        tecnico.setDisponible(false);
        usuarioRepository.save(tecnico);
        incidenteRepository.save(incidente);

        // Registrar asignación en la trazabilidad
        historyService.logEvent(
                incidente,
                EventType.TECH_ASSIGNED,
                "Asignado automáticamente al técnico: " + tecnico.getNombre(),
                tecnico.getId(), "TECNICO",
                EstadoOperativo.ABIERTO, EstadoOperativo.ASIGNADO
        );

        // Enviar WebSocket privado al técnico asignado
        notificationService.enviarNotificacionAsignacion(
                tecnico.getNumeroReloj(),
                "Tienes un nuevo incidente asignado: " + incidente.getTitulo()
        );
    }

    private long contarIncidentesActivos(Long tecnicoId) {
        return incidenteRepository.findByTecnicoId(tecnicoId)
                .stream()
                .filter(i -> i.getEstadoOperativo() != EstadoOperativo.RESUELTO
                        && i.getEstadoOperativo() != EstadoOperativo.CERRADO)
                .count();
    }
}