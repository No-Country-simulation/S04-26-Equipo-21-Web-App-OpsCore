package com.opscore.incident.service;

import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.EstadoValidacion;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.repository.IncidenteRepository;
import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificationService notificationService;

    // REASIGNACIÓN DE TÉCNICO (CASO REAL INDUSTRIAL)
    @Transactional
    public void reasignarTecnico(Long incidenteId, Long nuevoTecnicoId, Long supervisorId) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        Usuario nuevoTecnico = usuarioRepository.findById(nuevoTecnicoId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        Usuario supervisor = usuarioRepository.findById(supervisorId)
                .orElseThrow(() -> new RuntimeException("Supervisor no encontrado"));

        Usuario tecnicoAnterior = incidente.getTecnico();

        // 1. Liberar técnico anterior si existía
        if (tecnicoAnterior != null) {
            tecnicoAnterior.setDisponible(true);
            usuarioRepository.save(tecnicoAnterior);
        }

        // 2. Asignar nuevo técnico
        incidente.setTecnico(nuevoTecnico);
        incidente.setEstadoOperativo(EstadoOperativo.ASIGNADO);
        incidente.setFechaAsignacion(LocalDateTime.now());

        nuevoTecnico.setDisponible(false);
        usuarioRepository.save(nuevoTecnico);

        incidenteRepository.save(incidente);

        // 3. Notificación
        notificationService.enviarNotificacionAsignacion(
                nuevoTecnico.getNumeroReloj(),
                "Reasignación de incidente: " + incidente.getTitulo()
        );
    }


    // VALIDACIÓN DE INCIDENTE (SUPERVISOR)
    @Transactional
    public void validarIncidente(Long incidenteId, Long supervisorId) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        incidente.setEstadoValidacion(EstadoValidacion.VALIDADO);
        incidente.setFechaCierre(LocalDateTime.now());

        incidenteRepository.save(incidente);

        notificationService.notificarIncidenteGeneral(
                "Incidente validado: " + incidente.getTitulo()
        );
    }


    // RECHAZO DE RESOLUCIÓN (SE REGRESA A TÉCNICO)
    @Transactional
    public void rechazarResolucion(Long incidenteId, String motivo) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        incidente.setEstadoValidacion(EstadoValidacion.RECHAZADO);
        incidente.setEstadoOperativo(EstadoOperativo.ASIGNADO);

        incidenteRepository.save(incidente);

        notificationService.enviarNotificacionAsignacion(
                incidente.getTecnico().getNumeroReloj(),
                "Resolución rechazada: " + motivo
        );
    }


    // CIERRE FINAL DEL INCIDENTE
    @Transactional
    public void cerrarIncidente(Long incidenteId, Long supervisorId) {

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        Usuario tecnico = incidente.getTecnico();

        // liberar técnico si aún no está libre
        if (tecnico != null) {
            tecnico.setDisponible(true);
            usuarioRepository.save(tecnico);
        }

        incidente.setEstadoValidacion(EstadoValidacion.VALIDADO);
        incidente.setFechaCierre(LocalDateTime.now());

        incidenteRepository.save(incidente);

        notificationService.notificarIncidenteGeneral(
                "Incidente cerrado: " + incidente.getTitulo()
        );
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> upstream/develop
