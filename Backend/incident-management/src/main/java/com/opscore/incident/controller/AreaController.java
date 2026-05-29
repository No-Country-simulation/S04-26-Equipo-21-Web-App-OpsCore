package com.opscore.incident.controller;

import com.opscore.incident.model.Area;
import com.opscore.incident.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaRepository areaRepository;

    @GetMapping
    public List<Area> listar() {
        return areaRepository.findAll();
    }

    @PostMapping
    public Area crear(@RequestBody Area area) {
        return areaRepository.save(area);
    }
}