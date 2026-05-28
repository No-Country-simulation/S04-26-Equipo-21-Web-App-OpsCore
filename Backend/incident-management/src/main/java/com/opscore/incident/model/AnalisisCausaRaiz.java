//package com.opscore.incident.model;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class AnalisisCausaRaiz {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    private Incidente incidente;
//
//    private String porque1;
//    private String porque2;
//    private String porque3;
//    private String porque4;
//    private String porque5;
//
//    private String causaRaizFinal;
//    private String accionCorrectiva;
//
//    @ManyToOne
//    private Usuario responsable;
//
//    private LocalDateTime createdAt;
//}
//