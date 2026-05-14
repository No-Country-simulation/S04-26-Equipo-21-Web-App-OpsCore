package com.opscore.incident.service;

import com.opscore.incident.dto.IncidenteResponseDTO;
import com.opscore.incident.mapper.IncidenteMapper;
import com.opscore.incident.model.ChecklistEjecucion;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.model.Usuario;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import com.opscore.incident.repository.IncidenteRepository;
import com.opscore.incident.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void crearIncidenteDesdeChecklist(ChecklistEjecucion ejecucion) {
        // Creamos el objeto incidente basado en la falla del checklist
        Incidente nuevoIncidente = Incidente.builder()
                .estacion(ejecucion.getEstacion())
                .operador(ejecucion.getOperador())
                .descripcion("Falla detectada en inspección inicial: " + ejecucion.getPlantilla().getTitulo())
                .estado(EstadoIncidente.ABIERTO)
                .prioridad(Prioridad.CRITICO) // Por defecto alta al ser preventivo
                .build();

        // Intentamos asignación automática (Lógica de especialidad)
        // Nota: Aquí podrías definir qué especialidad requiere según el checklist
        this.asignarTecnicoAutomatico(nuevoIncidente, 1L); // 1L es un ejemplo de ID de especialidad

        incidenteRepository.save(nuevoIncidente);
    }

    // Dentro de IncidenteService.java

    private final NotificationService notificationService; // Inyectar el nuevo servicio

    @Transactional
    public void asignarTecnicoAutomatico(Incidente incidente, Long especialidadId) {
        List<Usuario> aptos = usuarioRepository.findTecnicoAsignable(
                incidente.getArea().getId(), especialidadId);

        if (!aptos.isEmpty()) {
            Usuario tecnico = aptos.get(0);
            incidente.setTecnico(tecnico);
            incidente.setEstado(EstadoIncidente.EN_PROCESO);
            tecnico.setDisponible(false);

            usuarioRepository.save(tecnico);

            // NOTIFICACIÓN EN TIEMPO REAL
            String aviso = "Nuevo incidente crítico asignado en " + incidente.getEstacion().getNombre();
            notificationService.enviarNotificacionAsignacion(tecnico.getNumeroReloj(), aviso);
        }
    }

    @Transactional
    public Incidente reportarFalla(Incidente incidente, Long especialidadId) {
        // Buscamos técnicos que cumplan: Área + Especialidad + Conectado + Disponible
        List<Usuario> tecnicosAptos = usuarioRepository.findTecnicoAsignable(
                incidente.getArea().getId(),
                especialidadId
        );

        if (!tecnicosAptos.isEmpty()) {
            Usuario asignado = tecnicosAptos.get(0);
            incidente.setTecnico(asignado);
            incidente.setEstado(EstadoIncidente.EN_PROCESO);

            // Cambiamos disponibilidad para que no reciba otro ticket por ahora
            asignado.setDisponible(false);
            usuarioRepository.save(asignado);
        } else {
            // Si no hay nadie disponible con esa especialidad en esa área
            incidente.setEstado(EstadoIncidente.ABIERTO);
        }

        return incidenteRepository.save(incidente);
    }

    // Dentro de IncidenteService.java

    private final IncidenteMapper incidenteMapper;

    @Transactional
    public IncidenteResponseDTO reportarFallaManual(Incidente incidente, Long especialidadId) {
        // Buscamos técnicos aptos
        List<Usuario> tecnicosAptos = usuarioRepository.findTecnicoAsignable(
                incidente.getArea().getId(),
                especialidadId
        );

        if (!tecnicosAptos.isEmpty()) {
            Usuario asignado = tecnicosAptos.get(0);
            incidente.setTecnico(asignado);
            incidente.setEstado(EstadoIncidente.EN_PROCESO);
            asignado.setDisponible(false);
            usuarioRepository.save(asignado);

            // Notificación
            notificationService.enviarNotificacionAsignacion(
                    asignado.getNumeroReloj(),
                    "Nueva falla reportada en: " + incidente.getEstacion().getNombre()
            );
        } else {
            incidente.setEstado(EstadoIncidente.ABIERTO);
        }

        Incidente guardado = incidenteRepository.save(incidente);

        // DEVOLVEMOS DTO EN LUGAR DE ENTIDAD
        return incidenteMapper.toDTO(guardado);
    }

}
