package com.challenges.backend.ame.starwars.project.model.planet;

import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.relational.core.mapping.Table;

@Table("planets")
@TypeAlias("planet")
public record Planet(

        @Id
        Long id,

        String name,

        String climate,

        String terrain

) {

    public Planet(CreatePlanetReqDTO createPlanetReqDTO) {
        this(
                null,
                createPlanetReqDTO.name(),
                createPlanetReqDTO.climate(),
                createPlanetReqDTO.terrain()
        );
    }
}
