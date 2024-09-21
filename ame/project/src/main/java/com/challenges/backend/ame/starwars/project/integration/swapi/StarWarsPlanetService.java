package com.challenges.backend.ame.starwars.project.integration.swapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StarWarsPlanetService {

    private final StarWarsClient starWarsClient;

    public Mono<Integer> getPlanetAppearancesInFilms(String planetName) {
        return starWarsClient.getPlanets()
                .map(planetDTOs -> planetDTOs.stream()
                        .filter(planetDTO -> planetDTO.name().equalsIgnoreCase(planetName))
                        .findFirst()
                        .map(planetDTO -> planetDTO.films().size())
                        .orElse(0));
    }

}
