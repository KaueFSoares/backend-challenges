package com.challenges.backend.ame.starwars.project.controller;

import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.challenges.backend.ame.starwars.project.model.planet.dto.PlanetResDTO;
import com.challenges.backend.ame.starwars.project.service.PlanetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService service;

    @GetMapping
    public Mono<ResponseEntity<Page<Planet>>> findAll(Pageable pageable) {
        return service.findAll(pageable)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Planet>> findById(
            @PathVariable
            Long id
    ) {
        return service.findById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<PlanetResDTO>> findByName(
            @PathVariable
            String name
    ) {
        return service.findByName(name)
                .map(ResponseEntity::ok);
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
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable
            Long id
    ) {
        return service.delete(id)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }

}
