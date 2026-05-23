package com.opscore.incident.repository;

import com.opscore.incident.enums.EstadoOperativo;
import com.opscore.incident.model.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardMetricsRepository extends JpaRepository<Incidente, Long> {

    // =========================
    // KPI POR ESTADO
    // =========================
    @Query("""
        SELECT i.estadoOperativo, COUNT(i)
        FROM Incidente i
        GROUP BY i.estadoOperativo
    """)
    List<Object[]> countByEstado();

    // =========================
    // MTTR
    // =========================
    @Query("""
        SELECT COALESCE(AVG(
            EXTRACT(EPOCH FROM (i.fechaResolucion - i.fechaInicioTrabajo))/60
        ), 0)
        FROM Incidente i
        WHERE i.fechaResolucion IS NOT NULL
        AND i.fechaInicioTrabajo IS NOT NULL
    """)
    Double calcularMTTR();

    // =========================
    // MTTA
    // =========================
    @Query("""
        SELECT COALESCE(AVG(
            EXTRACT(EPOCH FROM (i.fechaAsignacion - i.fechaCreacion))/60
        ), 0)
        FROM Incidente i
        WHERE i.fechaAsignacion IS NOT NULL
    """)
    Double calcularMTTA();

    // =========================
    // INCIDENTES POR AREA
    // =========================
    @Query("""
        SELECT a.nombre, COUNT(i)
        FROM Incidente i
        JOIN i.area a
        GROUP BY a.nombre
    """)
    List<Object[]> incidentesPorArea();

    // =========================
    // TOP MAQUINAS
    // =========================
    @Query("""
        SELECT e.nombre, COUNT(i)
        FROM Incidente i
        JOIN i.estacion e
        GROUP BY e.nombre
    """)
    List<Object[]> topMaquinasConFallas();

    // =========================
    // SLA CRITICOS
    // =========================
    @Query("""
        SELECT COUNT(i)
        FROM Incidente i
        WHERE i.prioridad = com.opscore.incident.enums.Prioridad.CRITICO
        AND i.fechaResolucion IS NOT NULL
        AND EXTRACT(EPOCH FROM (i.fechaResolucion - i.fechaCreacion))/60 > 30
    """)
    Long incidentesCriticosFueraSla();
}