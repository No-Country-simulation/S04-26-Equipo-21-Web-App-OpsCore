package com.opscore.incident.controller;

import com.opscore.incident.dto.RCARequestDTO;
import com.opscore.incident.model.IncidentRca;
import com.opscore.incident.service.RcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rca")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RcaController {

    private final RcaService rcaService;

    // REGISTRAR ANÁLISIS DE LOS 5 PORQUÉS
    @PostMapping
    public IncidentRca registrarRca(@RequestBody RCARequestDTO request) {
        return rcaService.registrarRca(request);
    }

    // CONSULTAR ANÁLISIS POR INCIDENTE
    @GetMapping("/incidente/{incidenteId}")
    public IncidentRca obtenerPorIncidente(@PathVariable Long incidenteId) {
        return rcaService.obtenerRca(incidenteId);
    }
}