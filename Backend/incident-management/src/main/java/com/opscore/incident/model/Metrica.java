//package com.opscore.incident.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(
//        name = "metricas",
//        indexes = {
//                @Index(name = "idx_metricas_periodo", columnList = "periodo")
//        }
//)
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Metrica {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, length = 50)
//    private String periodo;
//
//    private Double tiempoPromedioResolucion;
//
//    private Double tasaCierre;
//
//    private Integer incidentesCriticos;
//
//    @Column(columnDefinition = "TEXT")
//    private String patronesRecurrentes; // texto o JSON
//}
//