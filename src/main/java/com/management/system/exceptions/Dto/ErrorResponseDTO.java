package com.management.system.exceptions.Dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Data
public class ErrorResponseDTO {

    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private Map<String, String> fieldErrors;



}
