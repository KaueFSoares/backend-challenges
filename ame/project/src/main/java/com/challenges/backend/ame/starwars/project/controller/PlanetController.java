package com.challenges.backend.ame.starwars.project.controller;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.challenges.backend.ame.starwars.project.service.PlanetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService service;

    @GetMapping
    public ResponseEntity<Flux<Planet>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public Mono<ResponseEntity<Planet>> create(
            @Valid
            @RequestBody
            CreatePlanetReqDTO createPlanetReqDTO
    ) {
        return service.create(createPlanetReqDTO)
                .map(planet -> {
                    URI uri = URI.create("/api/v1/planets/" + planet.id());
                    return ResponseEntity.created(uri).body(planet);
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

}
