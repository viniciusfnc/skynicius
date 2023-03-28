package com.pt.skynicius.model.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.experimental.Accessors;


@Table
@Data
@Accessors(chain = true)
public class Airport extends BaseEntity implements Serializable {

    @Id
    @NotEmpty(message = "Name may not be empty")
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Location may not be empty")
    @Size(max = 3)
    private String location;
}
