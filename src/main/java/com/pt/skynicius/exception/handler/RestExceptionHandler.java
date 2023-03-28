package com.pt.skynicius.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pt.skynicius.exception.AirportNotFoundException;
import com.pt.skynicius.model.dto.ErrorDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(AirportNotFoundException.class)
    ResponseEntity<ErrorDto> airportNotFound(AirportNotFoundException ex) {
        log.debug("handling exception::" + ex);
        var error = ErrorDto.builder()
            .httpStatus(HttpStatus.NOT_FOUND.value())
            .message(ex.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
