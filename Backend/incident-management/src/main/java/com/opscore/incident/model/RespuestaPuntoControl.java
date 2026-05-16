package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "respuestas_puntos_control",
        indexes = {
                @Index(name = "idx_respuestas_ejecucion_id", columnList = "ejecucion_id"),
                @Index(name = "idx_respuestas_item_id", columnList = "item_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_respuesta_ejecucion_item",
                        columnNames = {"ejecucion_id", "item_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaPuntoControl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ejecucion_id", nullable = false)
    private ChecklistEjecucion ejecucion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private ChecklistItem item;

    @Column(nullable = false)
    private boolean completado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}