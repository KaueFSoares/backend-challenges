package com.challenges.backend.ame.starwars.project.model.planet.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreatePlanetReqDTO (

        @NotEmpty
        String name,

        @NotEmpty
        String climate,

        @NotEmpty
        String terrain

) {
}
