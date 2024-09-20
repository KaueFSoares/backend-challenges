package com.challenges.backend.ame.starwars.project.service;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import com.challenges.backend.ame.starwars.project.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    public Flux<Planet> findAll() {
        return planetRepository.findAll();
    }

}
