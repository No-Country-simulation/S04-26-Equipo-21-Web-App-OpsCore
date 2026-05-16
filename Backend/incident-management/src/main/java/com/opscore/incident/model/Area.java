package com.opscore.incident.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String nombre;
}
