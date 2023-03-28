package com.pt.skynicius.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    private int httpStatus;
    private String message;
}
