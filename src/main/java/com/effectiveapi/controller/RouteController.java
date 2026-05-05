package com.effectiveapi.controller;

import com.effectiveapi.entity.ApiRoute;
import com.effectiveapi.repository.RouteRepository;
import com.effectiveapi.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/monitor")
@CrossOrigin(origins = "*") // Critical for AWS Amplify frontend
//@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {

    @Autowired
    private RouteRepository repository;

    @Autowired
    private RouteService service;

    // Endpoint for the Dashboard to get all data
    @GetMapping("/routes")
    public List<ApiRoute> getDashboardData() {
        return repository.findAll();
    }

    // Endpoint for the Ingestor (AWS Lambda) to post new log data
    @PostMapping("/ingest")
    public void ingestLog(@RequestBody Map<String, Object> payload) {
        String path = (String) payload.get("path");
        String method = (String) payload.get("method");
        int latency = (int) payload.get("latency");
        service.processLogEntry(path, method, latency);
    }
}