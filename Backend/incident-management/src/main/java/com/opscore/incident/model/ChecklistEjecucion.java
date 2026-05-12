package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "checklists_ejecucion")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistEjecucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_plantilla_id")
    private Checklist plantilla; // De qué checklist viene

    // Relacionamos con la máquina específica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id")
    private EstacionTrabajo estacion;

    // Relacionamos con el operador que realizó la tarea
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id")
    private Usuario operador;

    private LocalDateTime fechaRealizacion;

    // "cascade = CascadeType.ALL" permite que al guardar la checklist,
    // se guarden automáticamente todas las respuestas.
    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaPuntoControl> respuestas;

    @PrePersist
    protected void onCreate() {
        this.fechaRealizacion = LocalDateTime.now();
    }
}
