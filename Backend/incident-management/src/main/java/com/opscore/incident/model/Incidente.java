package com.opscore.incident.model;

import com.opscore.incident.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "incidentes")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Incidente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👇 IMPORTANTE: título humano legible
    private String titulo;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    private TipoFalla tipoFalla;

    @Enumerated(EnumType.STRING)
    private EstadoOperativo estadoOperativo;

    @Enumerated(EnumType.STRING)
    private EstadoValidacion estadoValidacion;

    // RELACIONES
    @ManyToOne
    private Area area;

    @ManyToOne
    private EstacionTrabajo estacion;

    @ManyToOne
    private Usuario operador;

    @ManyToOne
    private Usuario tecnico;

    // TIMELINE REAL
    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaInicioTrabajo;
    private LocalDateTime fechaResolucion;
    private LocalDateTime fechaCierre;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL)
    private List<IncidentHistory> historial;
}