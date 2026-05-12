package com.opscore.incident.model;

import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidentes")
@Getter @Setter // Cambiamos @Data por Getter/Setter para evitar problemas con Lazy Loading
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Incidente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo; // Añadimos un título corto para identificar el ticket

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoIncidente estado;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Column(columnDefinition = "TEXT")
    private String solucionTecnica;

    private LocalDateTime fechaCierre; // Mantenemos esta para el cierre manual

    // --- RELACIONES OPTIMIZADAS ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area; // Nueva relación normalizada

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    private EstacionTrabajo estacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportado_por_id")
    private Usuario operador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_asignado_id")
    private Usuario tecnico;

    // Nota: El estado inicial se puede manejar mejor en la capa de Servicio
    // o dejando el default en el constructor.
}