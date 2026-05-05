package com.effectiveapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_routes")
@Data
public class ApiRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String method;
    private LocalDateTime lastAccessed;
    private int avgLatencyMs;
    private long totalHits;
    private boolean isZombie;


    // Getters and Setters...
}