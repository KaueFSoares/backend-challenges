package com.challenges.backend.ame.starwars.project.model.planet.dto;

public record PlanetResDTO(
        Long id,
        String name,
        String climate,
        String terrain,
        Integer films
) {
}
