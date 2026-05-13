package com.opscore.incident.controller;

import com.opscore.incident.service.AsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    // Recibe un JSON: { "empleados": ["123", "456", "789"] }
    @PostMapping("/sincronizar-entrada")
    public ResponseEntity<String> sincronizarEntrada(@RequestBody Map<String, List<String>> payload) {
        List<String> listaReloj = payload.get("empleados");
        asistenciaService.procesarEntradaMasiva(listaReloj);
        return ResponseEntity.ok("Sincronización completada: " + listaReloj.size() + " técnicos activos.");
    }
}