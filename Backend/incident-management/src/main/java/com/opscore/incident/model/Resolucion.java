package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "resoluciones",
        indexes = {
                @Index(name = "idx_resoluciones_incidente_id", columnList = "incidente_id"),
                @Index(name = "idx_resoluciones_tecnico_id", columnList = "tecnico_id") // 👈 CORREGIDO AQUÍ
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

    @ManyToOne
    private Incidente incidente;

    private String descripcionSolucion;

    @ManyToOne
    private Usuario tecnico; // 👈 Al llamarse 'tecnico', la columna en la BD es 'tecnico_id'

    private LocalDateTime createdAt;
}