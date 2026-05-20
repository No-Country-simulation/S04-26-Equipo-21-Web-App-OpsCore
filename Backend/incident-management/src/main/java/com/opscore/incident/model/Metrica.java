package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metricas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metrica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodo; // semanal, mensual
    private Double tiempoPromedioResolucion;
    private Double tasaCierre;
    private Integer incidentesCriticos;

    @Column(columnDefinition = "TEXT")
    private String patronesRecurrentes; // texto o JSON
}
