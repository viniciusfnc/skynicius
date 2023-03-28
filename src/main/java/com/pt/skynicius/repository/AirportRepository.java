package com.pt.skynicius.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import com.pt.skynicius.model.entity.Airport;

import reactor.core.publisher.Mono;

@Repository
public interface AirportRepository extends ReactiveSortingRepository<Airport, Long> {

    Mono<Airport> findByLocation(final String location);
}
