package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "checklists_ejecucion",
        indexes = {
                @Index(
                        name = "idx_checklists_ejecucion_plantilla_id",
                        columnList = "checklist_plantilla_id"
                ),
                @Index(
                        name = "idx_checklists_ejecucion_estacion_id",
                        columnList = "estacion_id"
                ),
                @Index(
                        name = "idx_checklists_ejecucion_operador_id",
                        columnList = "operador_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistEjecucion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "checklist_plantilla_id",
            nullable = false
    )
    private Checklist plantilla;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "estacion_id",
            nullable = false
    )
    private EstacionTrabajo estacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "operador_id",
            nullable = false
    )
    private Usuario operador;

    @Builder.Default
    @OneToMany(
            mappedBy = "ejecucion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<RespuestaPuntoControl> respuestas = new ArrayList<>();

    public void addRespuesta(RespuestaPuntoControl respuesta) {
        respuestas.add(respuesta);
        respuesta.setEjecucion(this);
    }

    public void removeRespuesta(RespuestaPuntoControl respuesta) {
        respuestas.remove(respuesta);
        respuesta.setEjecucion(null);
    }
}