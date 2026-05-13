package com.opscore.incident.controller;


import com.opscore.incident.model.Incidente;
import com.opscore.incident.enums.EstadoIncidente;
import com.opscore.incident.enums.Prioridad;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/incidentes")
public class IncidenteController {

    private final IncidenteService incidenteService;


    @PostMapping
    public Incidente reportarIncidente(@RequestBody Incidente incidente) {
        return incidenteService.reportarIncidente(incidente);
    }

    @GetMapping("/estado/{estado}")
    public List<Incidente> listarPorEstado(@PathVariable EstadoIncidente estado) {
        return incidenteService.listarPorEstado(estado);
    }

    @GetMapping("/prioridad/{prioridad}")
    public List<Incidente> listarPorPrioridad(@PathVariable Prioridad prioridad) {
        return incidenteService.listarPorPrioridad(prioridad);
    }

    @PutMapping("/{id}/estado")
    public Incidente actualizarEstado(@PathVariable Long id, @RequestParam EstadoIncidente nuevoEstado) {
        return incidenteService.actualizarEstado(id, nuevoEstado);
    }
}
