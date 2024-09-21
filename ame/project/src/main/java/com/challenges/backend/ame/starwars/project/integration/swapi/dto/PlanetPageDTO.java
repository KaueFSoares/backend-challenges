package com.challenges.backend.ame.starwars.project.integration.swapi.dto;

import java.util.List;

public record PlanetPageDTO(
    List<PlanetDTO> results,
    String next,
    String previous,
    Integer count
) {
}
