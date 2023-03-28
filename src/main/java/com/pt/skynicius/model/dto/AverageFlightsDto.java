package com.pt.skynicius.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.relational.core.mapping.Table;

import com.pt.skynicius.model.entity.BaseEntity;

import lombok.Data;

@Table
@Data
public class AverageFlightsDto extends BaseEntity implements Serializable {

    private String location;
    private String airportName;
    private Long totalFlights = 0L;
    private BigDecimal avgFlightPrices  = BigDecimal.ZERO;


}
