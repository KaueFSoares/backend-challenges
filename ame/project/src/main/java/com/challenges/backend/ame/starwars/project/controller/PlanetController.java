package com.challenges.backend.ame.starwars.project.controller;

import com.challenges.backend.ame.starwars.project.model.Planet;
import com.challenges.backend.ame.starwars.project.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @GetMapping
    public ResponseEntity<Flux<Planet>> findAll() {
        return ResponseEntity.ok(planetService.findAll());
    }

}
