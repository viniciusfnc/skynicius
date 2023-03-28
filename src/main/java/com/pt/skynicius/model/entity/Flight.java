package com.pt.skynicius.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table
@Data
public class Flight extends BaseEntity implements Serializable {

    @Id
    private Long id;

    @NotNull
    private BigDecimal price;

    @NotEmpty(message = "Location may not be empty")
    @Size(max = 3)
    private String location;

}
