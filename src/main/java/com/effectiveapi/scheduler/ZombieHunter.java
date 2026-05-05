package com.effectiveapi.scheduler;

import com.effectiveapi.entity.ApiRoute;
import com.effectiveapi.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ZombieHunter {

    @Autowired
    private RouteRepository repository;

    @Value("${zombie.threshold.days}")
    private int thresholdDays;

    // Runs every day at 1:00 AM
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(fixedRate = 10000)
    public void identifyZombies() {
        // Any route not accessed in the last 30 days is a 'Zombie'
        LocalDateTime threshold = LocalDateTime.now().minusDays(thresholdDays);
        List<ApiRoute> potentialZombies = repository.findAllByLastAccessedBeforeAndIsZombieFalse(threshold);

        potentialZombies.forEach(route -> {
            route.setZombie(true);
            repository.save(route);
        });

        System.out.println("Zombie Hunter: Flagged " + potentialZombies.size() + " new zombie routes.");
    }
}