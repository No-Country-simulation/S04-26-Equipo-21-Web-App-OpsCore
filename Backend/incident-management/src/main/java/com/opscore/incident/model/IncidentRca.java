package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_rca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentRca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "incidente_id")
    private Incidente incidente;

    // 5 porqués (estructura simple MVP)
    @Column(columnDefinition = "TEXT")
    private String porque1;

    @Column(columnDefinition = "TEXT")
    private String porque2;

    @Column(columnDefinition = "TEXT")
    private String porque3;

    @Column(columnDefinition = "TEXT")
    private String porque4;

    @Column(columnDefinition = "TEXT")
    private String porque5;

    @Column(columnDefinition = "TEXT")
    private String causaRaiz;

    @Column(columnDefinition = "TEXT")
    private String accionCorrectiva;

    private LocalDateTime fechaAnalisis;

    private Long analizadoPorSupervisorId;
<<<<<<< HEAD
}
=======
}
>>>>>>> upstream/develop
