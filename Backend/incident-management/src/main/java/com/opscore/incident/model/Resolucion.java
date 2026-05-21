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

    @ManyToOne
    private Incidente incidente;

    private String descripcionSolucion;

    @ManyToOne
    private Usuario tecnico;

    private LocalDateTime createdAt;
}
