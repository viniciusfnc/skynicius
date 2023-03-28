package com.pt.skynicius.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pt.skynicius.exception.AirportNotFoundException;
import com.pt.skynicius.model.dto.AverageFlightsDto;
import com.pt.skynicius.model.dto.FlightDto;
import com.pt.skynicius.service.FlightService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flights")
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "Parameters with invalid values.")
})
@Api(value = "Flights", description = "API to search flight information", tags = { "Flights" })
public class FlightController {

    private final FlightService flightService;

    public FlightController(final FlightService flightService) {
        this.flightService = flightService;
    }

    @ApiOperation(value = "Returns average flight prices")
    @GetMapping(value = "/avg", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AverageFlightsDto> getSummaryFlight(
        @ApiParam(value = "Location", example = "LIS")
        @RequestParam("location") String location
    ) throws AirportNotFoundException {
        return flightService.getAverageFlights(location);
    }

    @ApiOperation(value = "Find all flights by location")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<FlightDto> findFlights(
        @ApiParam(value = "Location", example = "LIS")
        @RequestParam("location") String location
    ) {
        return flightService.findFlightsByLocation(location);
    }

    @ApiOperation(value = "Delete all flights")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteAll() {
        return flightService.deleteAllFlights();
    }

}
