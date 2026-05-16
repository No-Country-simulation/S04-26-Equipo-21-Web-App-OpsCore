package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "estaciones_trabajo",
        indexes = {
                @Index(name = "idx_estaciones_trabajo_area_id", columnList = "area_id"),
                @Index(name = "idx_estaciones_trabajo_codigo", columnList = "codigo")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstacionTrabajo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50, updatable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;
}
