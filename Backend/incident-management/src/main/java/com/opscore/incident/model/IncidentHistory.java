package com.opscore.incident.model;

import com.opscore.incident.enums.EstadoOperativo;
<<<<<<< HEAD
import com.opscore.incident.enums.EventType;
import jakarta.persistence.*;
=======
import jakarta.persistence.*;
import jdk.jfr.EventType;
>>>>>>> upstream/develop
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidente_id")
    private Incidente incidente;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String descripcion;

    private Long usuarioId;

    private String usuarioRol;

    private LocalDateTime timestamp;

    // snapshot opcional del estado
    @Enumerated(EnumType.STRING)
    private EstadoOperativo estadoAnterior;

    @Enumerated(EnumType.STRING)
    private EstadoOperativo estadoNuevo;
}
