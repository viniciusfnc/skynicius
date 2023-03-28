package com.pt.skynicius.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.pt.skynicius.mapper.FlightMapperImpl;
import com.pt.skynicius.model.dto.FlightDto;
import com.pt.skynicius.model.entity.Airport;
import com.pt.skynicius.model.entity.Flight;
import com.pt.skynicius.repository.AirportRepository;
import com.pt.skynicius.repository.FlightRepository;
import com.pt.skynicius.service.impl.FlightServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Import({FlightServiceImpl.class, FlightMapperImpl.class, R2dbcMappingContext.class })
@WebFluxTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private AirportRepository airportRepository;

    @Test
    void getAverageFlights() {

        var flight = new Flight();
        flight.setId(1L);
        flight.setPrice(BigDecimal.ONE);
        flight.setLocation("LIS");

        var flight2 = new Flight();
        flight2.setId(1L);
        flight2.setPrice(BigDecimal.TEN);
        flight2.setLocation("LIS");

        Mockito
            .when(flightRepository.findByLocation("LIS"))
            .thenReturn(Flux.just(flight, flight2));

        var airport = new Airport();
        airport.setLocation("LIS");
        airport.setName("Lisbon");

        Mockito
            .when(airportRepository.findByLocation("LIS"))
            .thenReturn(Mono.just(airport));

        webClient.get()
            .uri("/flights/avg?location=LIS")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.totalFlights").isEqualTo(2)
            .jsonPath("$.avgFlightPrices").isEqualTo(5.5)
            .jsonPath("$.airportName").isEqualTo("Lisbon");

    }

    @Test
    void findFlights() {

        var flight = new Flight();
        flight.setId(1L);
        flight.setPrice(BigDecimal.ONE);
        flight.setLocation("LIS");

        var flight2 = new Flight();
        flight2.setId(1L);
        flight2.setPrice(BigDecimal.TEN);
        flight2.setLocation("LIS");

        Mockito
            .when(flightRepository.findByLocation("LIS"))
            .thenReturn(Flux.just(flight, flight2));

        webClient.get()
            .uri("/flights?location=LIS")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(FlightDto.class)
            .hasSize(2);

    }

    @Test
    void deleteAll() {
        webClient.delete()
            .uri("/flights")
            .exchange()
            .expectStatus().isOk();

    }
}