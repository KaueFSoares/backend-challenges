package com.challenges.backend.ame.starwars.project.repository;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface PlanetRepository extends ReactiveCrudRepository<Planet, Long> {
    Flux<Planet> findAllBy(Pageable pageable);

    Mono<Optional<Planet>> findOptionalById(Long id);

    Mono<Optional<Planet>> findByName(String name);
}
