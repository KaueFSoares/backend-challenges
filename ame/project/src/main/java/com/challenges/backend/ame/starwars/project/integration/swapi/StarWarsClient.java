package com.challenges.backend.ame.starwars.project.integration.swapi;

import com.challenges.backend.ame.starwars.project.integration.swapi.dto.PlanetDTO;
import com.challenges.backend.ame.starwars.project.integration.swapi.dto.PlanetPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StarWarsClient {

    private final WebClient webClient;

    @Cacheable("planets")
    public Mono<List<PlanetDTO>> getPlanets() {
        return fetchAllPlanets("https://swapi.dev/api/planets?page=1", new ArrayList<>());
    }

    private Mono<List<PlanetDTO>> fetchAllPlanets(String url, List<PlanetDTO> accumulatedPlanets) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(PlanetPageDTO.class)
                .flatMap(page -> {
                    accumulatedPlanets.addAll(page.results());

                    if (page.next() != null) {
                        return fetchAllPlanets(page.next(), accumulatedPlanets);
                    } else {
                        return Mono.just(accumulatedPlanets);
                    }
                });
    }

}
