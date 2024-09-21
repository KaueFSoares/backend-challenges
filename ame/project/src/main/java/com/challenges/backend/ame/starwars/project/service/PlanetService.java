package com.challenges.backend.ame.starwars.project.service;

import com.challenges.backend.ame.starwars.project.integration.swapi.StarWarsPlanetService;
import com.challenges.backend.ame.starwars.project.model.planet.Planet;
import com.challenges.backend.ame.starwars.project.model.planet.dto.CreatePlanetReqDTO;
import com.challenges.backend.ame.starwars.project.model.planet.dto.PlanetResDTO;
import com.challenges.backend.ame.starwars.project.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final StarWarsPlanetService starWarsPlanetService;

    public Mono<Page<PlanetResDTO>> findAll(Pageable pageable) {
        return planetRepository.findAllBy(pageable)
                .collectList()
                .zipWith(planetRepository.count())
                .flatMap(tuple -> Flux.fromIterable(tuple.getT1())
                        .flatMap(planet -> starWarsPlanetService.getPlanetAppearancesInFilms(planet.name())
                                .map(planet::to))
                        .collectList()
                        .map(planetResDTOs -> new PageImpl<>(planetResDTOs, pageable, tuple.getT2())));
    }

    public Mono<PlanetResDTO> findById(Long id) {
        return planetRepository.findById(id)
                .flatMap(planet ->
                        starWarsPlanetService.getPlanetAppearancesInFilms(planet.name())
                                .map(planet::to))
                .switchIfEmpty(Mono.error(new RuntimeException("Planet not found")));
    }

    public Mono<PlanetResDTO> findByName(String name) {
        return planetRepository.findByName(name)
                .flatMap(planet ->
                        starWarsPlanetService.getPlanetAppearancesInFilms(planet.name())
                                .map(planet::to))
                .switchIfEmpty(Mono.error(new RuntimeException("Planet not found")));
    }


    @Transactional
    public Mono<PlanetResDTO> create(CreatePlanetReqDTO createPlanetReqDTO) {
        return validatePlanetDoesNotExist(createPlanetReqDTO.name())
                .then(starWarsPlanetService.getPlanetAppearancesInFilms(createPlanetReqDTO.name())
                        .flatMap(films -> planetRepository.save(new Planet(createPlanetReqDTO))
                                .map(savedPlanet -> savedPlanet.to(films))));
    }

    public Mono<Void> delete(Long id) {
        return planetRepository.deleteById(id);
    }

    private Mono<Void> validatePlanetDoesNotExist(String planetName) {
        return planetRepository.existsByName(planetName)
                .flatMap(exists -> exists ?
                        Mono.error(new RuntimeException("Planet already exists")) :
                        Mono.empty());
    }
}
