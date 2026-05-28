package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respuestas_puntos_control")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaPuntoControl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean completado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
