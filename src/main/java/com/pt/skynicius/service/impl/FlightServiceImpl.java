package com.pt.skynicius.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.skynicius.exception.AirportNotFoundException;
import com.pt.skynicius.mapper.FlightMapper;
import com.pt.skynicius.model.dto.AverageFlightsDto;
import com.pt.skynicius.model.dto.FlightDto;
import com.pt.skynicius.model.entity.Airport;
import com.pt.skynicius.repository.AirportRepository;
import com.pt.skynicius.repository.FlightRepository;
import com.pt.skynicius.service.FlightService;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    public FlightServiceImpl(
        AirportRepository airportRepository,
        FlightRepository flightRepository
    ) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Caching(cacheable = { @Cacheable(value = "flights") })
    public Flux<FlightDto> findFlightsByLocation(final String location) {
        log.debug("Find flights by location {}", location);
        return flightRepository.findByLocation(location)
            .map(flightMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<Void> deleteAllFlights() {
        log.debug("Delete all flights");
        return flightRepository.deleteAll();
    }

    @Override
    @Caching(cacheable = { @Cacheable(value = "averageFlights") })
    public Mono<AverageFlightsDto> getAverageFlights(final String location) {
        log.debug("Get average flights {}", location);

        Flux<FlightDto> flights = this.findFlightsByLocation(location);

        Mono<AverageFlightsDto> averageFlghts = flights.collectList().map(result -> {
            var statistic = getStatistic(result);

            var averageFlights = new AverageFlightsDto();
            averageFlights.setAvgFlightPrices(BigDecimal.valueOf(statistic.getAverage()).setScale(2, RoundingMode.HALF_EVEN));
            averageFlights.setTotalFlights(statistic.getCount());
            averageFlights.setLocation(location);

            return averageFlights;
        });

        Mono<Airport> airport = airportRepository.findByLocation(location)
            .switchIfEmpty(Mono.error(
                new AirportNotFoundException(String.format("Airport not found: %s", location))
            ));

        averageFlghts = averageFlghts
            .zipWith(airport)
            .map(result -> {
                result.getT1().setAirportName(result.getT2().getName());
                return result.getT1();
            });

        return averageFlghts;
    }

    private DoubleSummaryStatistics getStatistic(List<FlightDto> flights) {
        return flights.stream()
            .mapToDouble(num -> num.getPrice().doubleValue())
            .summaryStatistics();
    }

}
