package com.opscore.incident.service;

import com.opscore.incident.model.*;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResolucionService {

    private final ResolucionRepository resolucionRepository;
    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Resolucion finalizarReparacion(Resolucion resolucion) {
        Incidente incidente = resolucion.getIncidente();
        incidente.setEstado(EstadoIncidente.RESUELTO);

        // Liberar al técnico para que pueda recibir más tareas
        Usuario tecnico = incidente.getTecnico();
        if (tecnico != null) {
            tecnico.setDisponible(true);
            usuarioRepository.save(tecnico);
        }

        incidenteRepository.save(incidente);
        return resolucionRepository.save(resolucion);
    }
}