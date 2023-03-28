package com.pt.skynicius.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.pt.skynicius.exception.AirportNotFoundException;
import com.pt.skynicius.model.dto.AverageFlightsDto;
import com.pt.skynicius.model.dto.FlightDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {

    @Caching(cacheable = { @Cacheable(value = "flights") })
    Flux<FlightDto> findFlightsByLocation(String location);

    Mono<Void> deleteAllFlights();

    Mono<AverageFlightsDto> getAverageFlights(final String location) throws AirportNotFoundException;
}
