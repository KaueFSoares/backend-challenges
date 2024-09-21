package com.challenges.backend.ame.starwars.project.integration.swapi;

import com.challenges.backend.ame.starwars.project.integration.swapi.dto.PlanetPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StarWarsClient {

    private final WebClient webClient;

    @Cacheable("planetsPage")
    public Mono<PlanetPageDTO> fetchPlanetsPage(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(PlanetPageDTO.class)
                .onErrorMap(e -> new RuntimeException("Failed to fetch planets from SWAPI", e));
    }

}
