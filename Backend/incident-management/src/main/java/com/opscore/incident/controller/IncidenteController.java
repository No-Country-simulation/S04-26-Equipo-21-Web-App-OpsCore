package com.opscore.incident.controller;


import com.opscore.incident.service.IncidenteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/incidentes")
@Tag(
        name = "Catálogos de incidentes",
        description = "Endpoints auxiliares para obtener tipos de incidentes y niveles de prioridad utilizados en el registro de incidentes operativos"
)
public class IncidenteController {

    private final IncidenteService incidenteService;

// solo son propuesta
//    @PostMapping
//    public Incidente reportarIncidente(@RequestBody Incidente incidente) {
//        return incidenteService.reportarIncidente(incidente);
//    }
//
//    @GetMapping("/estado/{estado}")
//    public List<Incidente> listarPorEstado(@PathVariable EstadoIncidente estado) {
//        return incidenteService.listarPorEstado(estado);
//    }
//
//    @GetMapping("/prioridad/{prioridad}")
//    public List<Incidente> listarPorPrioridad(@PathVariable Prioridad prioridad) {
//        return incidenteService.listarPorPrioridad(prioridad);
//    }
//
//    @PutMapping("/{id}/estado")
//    public Incidente actualizarEstado(@PathVariable Long id, @RequestParam EstadoIncidente nuevoEstado) {
//        return incidenteService.actualizarEstado(id, nuevoEstado);
//    }
}
