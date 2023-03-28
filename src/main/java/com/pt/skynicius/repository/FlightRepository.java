package com.pt.skynicius.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import com.pt.skynicius.model.entity.Flight;

import reactor.core.publisher.Flux;

@Repository
public interface FlightRepository extends ReactiveSortingRepository<Flight, Long> {

    Flux<Flight> findByLocation(final String location);
}
