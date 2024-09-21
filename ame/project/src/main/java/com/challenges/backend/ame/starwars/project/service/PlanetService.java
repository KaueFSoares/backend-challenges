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
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final StarWarsPlanetService starWarsPlanetService;

    public Mono<Page<Planet>> findAll(Pageable pageable) {
        // TODO: fetch planets from SWAPI

        return this.planetRepository.findAllBy(pageable)
                .collectList()
                .zipWith(this.planetRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    public Mono<Planet> findById(Long id) {
        // TODO: fetch planet from SWAPI

        return planetRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("PlanetDTO not found")));
    }

    public Mono<PlanetResDTO> findByName(String name) {
        Mono<Integer> films = starWarsPlanetService.getPlanetAppearancesInFilms(name);

        return planetRepository.findByName(name)
                .flatMap(planet -> films.map(planet::to))
                .switchIfEmpty(Mono.error(new RuntimeException("PlanetDTO not found")));
    }


    public Mono<Planet> create(CreatePlanetReqDTO createPlanetReqDTO) {
        // TODO: validate if planet already exists
        // TODO: validate if name exists in SWAPI

        Planet planet = new Planet(createPlanetReqDTO);

        return planetRepository.save(planet);
    }

    public Mono<Void> delete(Long id) {
        return planetRepository.deleteById(id);
    }
}
