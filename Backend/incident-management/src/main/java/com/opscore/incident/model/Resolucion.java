package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resoluciones")
@Data
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
