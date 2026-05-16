package com.opscore.incident.model;

import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "incidentes",
        indexes = {
                @Index(name = "idx_incidentes_area_id", columnList = "area_id"),
                @Index(name = "idx_incidentes_estacion_id", columnList = "estacion_id"),
                @Index(name = "idx_incidentes_reportado_por_id", columnList = "reportado_por_id"),
                @Index(name = "idx_incidentes_tecnico_asignado_id", columnList = "tecnico_asignado_id"),
                @Index(name = "idx_incidentes_estado", columnList = "estado"),
                @Index(name = "idx_incidentes_prioridad", columnList = "prioridad"),
                @Index(name = "idx_incidentes_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incidente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "incidente_estado")
    private EstadoIncidente estado;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "incidente_prioridad")
    private Prioridad prioridad;

    @Column(columnDefinition = "TEXT")
    private String solucionTecnica;

    private LocalDateTime fechaCierre;

    // --- RELACIONES ---

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estacion_id", nullable = false)
    private EstacionTrabajo estacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reportado_por_id")
    private Usuario operador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_asignado_id")
    private Usuario tecnico;
}