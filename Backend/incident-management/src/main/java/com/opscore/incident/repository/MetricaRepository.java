package com.opscore.incident.repository;

import com.opscore.incident.model.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MetricaRepository extends JpaRepository<Metrica, Long> {
    @Query("""
SELECT i.estadoOperativo, COUNT(i)
FROM Incidente i
GROUP BY i.estadoOperativo
""")
    List<Object[]> countByEstado();

    @Query("""
SELECT AVG(
EXTRACT(EPOCH FROM (i.fechaResolucion - i.fechaInicioTrabajo))/60
)
FROM Incidente i
WHERE i.fechaResolucion IS NOT NULL
AND i.fechaInicioTrabajo IS NOT NULL
""")
    Double calcularMTTR();

    @Query("""
SELECT a.nombre, COUNT(i)
FROM Incidente i
JOIN i.area a
GROUP BY a.nombre
ORDER BY COUNT(i) DESC
""")
    List<Object[]> incidentesPorArea();

    @Query("""
SELECT e.nombre, COUNT(i)
FROM Incidente i
JOIN i.estacion e
GROUP BY e.nombre
ORDER BY COUNT(i) DESC
""")
    List<Object[]> topMaquinasConFallas();

    @Query("""
SELECT COUNT(i)
FROM Incidente i
WHERE i.prioridad = 'CRITICO'
AND EXTRACT(EPOCH FROM (i.fechaResolucion - i.fechaCreacion))/60 > 30
""")
    Long incidentesCriticosFueraSla();




}
