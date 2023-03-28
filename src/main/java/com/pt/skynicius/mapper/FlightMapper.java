package com.pt.skynicius.mapper;

import org.mapstruct.Mapper;

import com.pt.skynicius.model.dto.FlightDto;
import com.pt.skynicius.model.entity.Flight;

@Mapper(componentModel = "spring")
public interface FlightMapper extends EntityMapper<FlightDto, Flight> {
}
