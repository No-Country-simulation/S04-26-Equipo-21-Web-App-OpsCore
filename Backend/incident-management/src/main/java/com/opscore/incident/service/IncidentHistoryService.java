package com.opscore.incident.service;

import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.enums.EventType;
import com.opscore.incident.model.IncidentHistory;
import com.opscore.incident.model.Incidente;
import com.opscore.incident.repository.IncidentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IncidentHistoryService {

    private final IncidentHistoryRepository historyRepository;

    public void logEvent(
            Incidente incidente,
            EventType type,
            String descripcion,
            Long usuarioId,
            String rol,
            EstadoOperativo oldState,
            EstadoOperativo newState
    ) {

        IncidentHistory history = IncidentHistory.builder()
                .incidente(incidente)
                .eventType(type)
                .descripcion(descripcion)
                .usuarioId(usuarioId)
                .usuarioRol(rol)
                .estadoAnterior(oldState)
                .estadoNuevo(newState)
                .timestamp(LocalDateTime.now())
                .build();

        historyRepository.save(history);
    }
}
