package com.pt.skynicius.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.relational.core.mapping.Table;

import com.pt.skynicius.model.entity.BaseEntity;

import lombok.Data;

@Table
@Data
public class FlightDto extends BaseEntity implements Serializable {

    private String id;

    private String location;

    private BigDecimal price;

}
