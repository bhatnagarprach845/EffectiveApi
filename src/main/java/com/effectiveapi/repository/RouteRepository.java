package com.effectiveapi.repository;

import com.effectiveapi.entity.ApiRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface RouteRepository extends JpaRepository<ApiRoute, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO api_routes (path, method, last_accessed, avg_latency_ms, total_hits, is_zombie) " +
            "VALUES (:path, :method, NOW(), :latency, 1, false) " +
            "ON CONFLICT (path, method) DO UPDATE SET " +
            "avg_latency_ms = (api_routes.avg_latency_ms * api_routes.total_hits + :latency) / (api_routes.total_hits + 1), " +
            "total_hits = api_routes.total_hits + 1, " +
            "last_accessed = NOW(), " +
            "is_zombie = false", nativeQuery = true)
    void upsertRoute(@Param("path") String path, @Param("method") String method, @Param("latency") int latency);

    List<ApiRoute> findAllByLastAccessedBeforeAndIsZombieFalse(LocalDateTime cutoff);
}