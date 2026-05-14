package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "checklists_ejecucion")
@Getter // Usamos Getter y Setter explícitos en vez de @Data para evitar ciclos infinitos
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistEjecucion extends BaseEntity { // Heredamos de BaseEntity para las fechas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_plantilla_id")
    private Checklist plantilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    private EstacionTrabajo estacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id")
    private Usuario operador;

    // mappedBy debe coincidir EXACTAMENTE con el nombre del atributo en RespuestaPuntoControl
    @OneToMany(mappedBy = "ejecucion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaPuntoControl> respuestas;
}
