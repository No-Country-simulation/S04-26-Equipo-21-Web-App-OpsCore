package com.opscore.incident.controller;

import com.opscore.incident.dto.DashboardMetricDTO;
import com.opscore.incident.service.MetricaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final MetricaService metricaService;

    // KPI principal del dashboard
    @GetMapping("/metrics")
    public DashboardMetricDTO getDashboardMetrics() {
        return metricaService.getDashboard();
    }
}
