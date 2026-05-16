package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "resoluciones",
        indexes = {
                @Index(name = "idx_resoluciones_incidente_id", columnList = "incidente_id"),
                @Index(name = "idx_resoluciones_responsable_id", columnList = "responsable_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "incidente_id", nullable = false, unique = true)
    private Incidente incidente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;

    @Column(name = "descripcion_solucion", columnDefinition = "TEXT", nullable = false)
    private String descripcionSolucion;

    private LocalDateTime fechaAsignacion;

    private LocalDateTime fechaCierre;

    private Long tiempoResolucion;
}