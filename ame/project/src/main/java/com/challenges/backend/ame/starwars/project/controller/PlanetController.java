package com.challenges.backend.ame.starwars.project.controller;

import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.challenges.backend.ame.starwars.project.model.planet.dto.PlanetResDTO;
import com.challenges.backend.ame.starwars.project.service.PlanetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService service;

    private EntityModel<PlanetResDTO> toEntityModel(PlanetResDTO planet) {
        return EntityModel.of(planet,
                WebMvcLinkBuilder.linkTo(methodOn(PlanetController.class).findById(planet.id())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(PlanetController.class).findAll(Pageable.unpaged())).withRel("planets")
        );
    }

    @GetMapping
    public Mono<ResponseEntity<PagedModel<EntityModel<PlanetResDTO>>>> findAll(Pageable pageable) {
        return service.findAll(pageable)
                .map(page -> {
                    PagedModel<EntityModel<PlanetResDTO>> pagedModel = PagedModel.of(
                            page.getContent().stream().map(this::toEntityModel).toList(),
                            new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages())
                    );
                    return ResponseEntity.ok(pagedModel);
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EntityModel<PlanetResDTO>>> findById(
            @PathVariable Long id
    ) {
        return service.findById(id)
                .map(planet -> ResponseEntity.ok(toEntityModel(planet)));
    }

    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<EntityModel<PlanetResDTO>>> findByName(
            @PathVariable String name
    ) {
        return service.findByName(name)
                .map(planet -> ResponseEntity.ok(toEntityModel(planet)));
    }

    @PostMapping
    public Mono<ResponseEntity<EntityModel<PlanetResDTO>>> create(
            @Valid @RequestBody CreatePlanetReqDTO createPlanetReqDTO
    ) {
        return service.create(createPlanetReqDTO)
                .map(planet -> {
                    URI uri = URI.create("/planets/" + planet.id());
                    return ResponseEntity.created(uri).body(toEntityModel(planet));
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable Long id
    ) {
        return service.delete(id)
                .then(Mono.fromCallable(() -> ResponseEntity.noContent().build()));
    }

}
