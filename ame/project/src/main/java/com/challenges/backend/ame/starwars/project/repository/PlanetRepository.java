package com.challenges.backend.ame.starwars.project.repository;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlanetRepository extends ReactiveCrudRepository<Planet, Long> {
}
