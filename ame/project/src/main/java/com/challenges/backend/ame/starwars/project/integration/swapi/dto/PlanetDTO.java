package com.challenges.backend.ame.starwars.project.integration.swapi.dto;

import java.util.List;

public record PlanetDTO(
        String name,
        String rotation_period,
        String orbital_period,
        String diameter,
        String climate,
        String gravity,
        String terrain,
        String surface_water,
        String population,
        List<String> residents,
        List<String> films,
        String created,
        String edited,
        String url
) {
}
