package com.challenges.backend.ame.starwars.project.repository;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlanetRepository extends ReactiveCrudRepository<Planet, Long> {
    Flux<Planet> findAllBy(Pageable pageable);
}
