package com.challenges.backend.ame.starwars.project.service;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.challenges.backend.ame.starwars.project.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    public Flux<Planet> findAll() {
        return planetRepository.findAll();
    }

    public Mono<Planet> create(CreatePlanetReqDTO createPlanetReqDTO) {
        // TODO: validate if planet already exists
        // TODO: validate if name exists in SWAPI

        Planet planet = new Planet(createPlanetReqDTO);

        return planetRepository.save(planet);
    }
}
