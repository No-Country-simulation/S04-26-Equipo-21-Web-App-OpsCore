package com.opscore.incident.repository;

import com.opscore.incident.model.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardMetricsRepository extends JpaRepository<Incidente, Long> {

    // 1. KPI POR ESTADO (JPQL Estándar)
    @Query("""
        SELECT i.estadoOperativo, COUNT(i)
        FROM Incidente i
        GROUP BY i.estadoOperativo
    """)
    List<Object[]> countByEstado();

    // 2. MTTR (SQL Nativo de PostgreSQL)
    @Query(value = """
        SELECT COALESCE(
            AVG(EXTRACT(EPOCH FROM (fecha_resolucion - fecha_inicio_trabajo)) / 60), 
            0.0
        )
        FROM incidentes
        WHERE fecha_resolucion IS NOT NULL
          AND fecha_inicio_trabajo IS NOT NULL
    """, nativeQuery = true)
    Double calcularMTTR();

    // 3. MTTA (SQL Nativo de PostgreSQL)
    @Query(value = """
        SELECT COALESCE(
            AVG(EXTRACT(EPOCH FROM (fecha_asignacion - created_at)) / 60), 
            0.0
        )
        FROM incidentes
        WHERE fecha_asignacion IS NOT NULL
    """, nativeQuery = true)
    Double calcularMTTA();

    // 4. SLA CRÍTICOS (SQL Nativo de PostgreSQL)
    @Query(value = """
        SELECT COUNT(*)
        FROM incidentes
        WHERE prioridad = 'CRITICO'
          AND fecha_resolucion IS NOT NULL
          AND (EXTRACT(EPOCH FROM (fecha_resolucion - created_at)) / 60) > 30
    """, nativeQuery = true)
    Long incidentesCriticosFueraSla();

    // 5. INCIDENTES POR ÁREA (JPQL Estándar)
    @Query("""
        SELECT a.nombre, COUNT(i)
        FROM Incidente i
        JOIN i.area a
        GROUP BY a.nombre
    """)
    List<Object[]> incidentesPorArea();

    // 6. TOP MÁQUINAS CON FALLAS (JPQL Estándar)
    @Query("""
        SELECT e.nombre, COUNT(i)
        FROM Incidente i
        JOIN i.estacion e
        GROUP BY e.nombre
    """)
    List<Object[]> topMaquinasConFallas();
}