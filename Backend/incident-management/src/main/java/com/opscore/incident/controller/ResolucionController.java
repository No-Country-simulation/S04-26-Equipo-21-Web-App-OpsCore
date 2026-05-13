package com.opscore.incident.controller;
import com.opscore.incident.model.Resolucion;
import com.opscore.incident.service.ResolucionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/resoluciones")
public class ResolucionController {

    private final ResolucionService resolucionService;

//recomendado
   // @PostMapping("/asignar")
   // public Resolucion asignarResponsable(@RequestParam Long incidenteId, @RequestParam Long usuarioId) {
   //     return resolucionService.asignarResponsable(incidenteId, usuarioId);
   // }
//
   // @PutMapping("/{id}/cerrar")
   // public Resolucion cerrarIncidente(@PathVariable Long id, @RequestParam String descripcionSolucion) {
   //     return resolucionService.cerrarIncidente(id, descripcionSolucion);
   // }
}

