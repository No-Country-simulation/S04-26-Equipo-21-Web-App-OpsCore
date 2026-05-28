package com.opscore.incident.controller;

import com.opscore.incident.dto.ResolucionRequestDTO;
import com.opscore.incident.dto.ResolucionResponseDTO;
import com.opscore.incident.service.ResolucionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resoluciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResolucionController {

    private final ResolucionService resolucionService;

    // TÉCNICO INICIA TRABAJO EN PISO (Para calcular el MTTR exacto)
    @PostMapping("/iniciar")
    public void iniciarTrabajo(@RequestParam Long incidenteId, @RequestParam Long tecnicoId) {
        resolucionService.iniciarTrabajo(incidenteId, tecnicoId);
    }

    // TÉCNICO FINALIZA Y CIERRA DESDE POSTMAN/APP
    @PostMapping("/resolver")
    public ResolucionResponseDTO resolverIncidente(@RequestBody ResolucionRequestDTO request) {
        return resolucionService.resolverIncidente(request);
    }
}