package com.effectiveapi.service;

import com.effectiveapi.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteRepository repository;

    public void processLogEntry(String path, String method, int latency) {
        repository.upsertRoute(path, method, latency);
    }
}