package com.challenges.backend.ame.starwars.project.integration.swapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StarWarsPlanetService {

    private final StarWarsClient starWarsClient;

    public Mono<Integer> getPlanetAppearancesInFilms(String planetName) {
        String initialUrl = "https://swapi.dev/api/planets?page=1";

        return fetchPlanetAppearancesInFilmsRecursive(planetName, initialUrl);
    }

    private Mono<Integer> fetchPlanetAppearancesInFilmsRecursive(String planetName, String url) {
        return starWarsClient.fetchPlanetsPage(url)
                .flatMap(page -> Mono.justOrEmpty(page.results().stream()
                                .filter(planet -> planet.name().equals(planetName))
                                .findFirst())
                        .map(planet -> planet.films().size())
                        .switchIfEmpty(
                                page.next() != null
                                        ? fetchPlanetAppearancesInFilmsRecursive(planetName, page.next())
                                        : Mono.error(new RuntimeException("Planet not found"))
                        ));
    }


}
