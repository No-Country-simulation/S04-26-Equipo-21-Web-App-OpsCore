package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resoluciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "incidente_id")
    private Incidente incidente;

    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;

    @Column(name = "descripcion_solucion", columnDefinition = "TEXT")
    private String descripcionSolucion;

    private LocalDateTime fechaAsignacion;
    private LocalDateTime fechaCierre;

    private Long tiempoResolucion; // en minutos u horas
}
