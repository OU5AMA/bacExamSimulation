package com.schoolmanagement.backend.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;      // When it happened
    private int status;             // HTTP status code (e.g., 400)
    private String error;           // reason phrase (e.g., "Bad Request")
    private String message;         // Human-readable summary
    private String path;            // request path (/api/v1/users)
    private List<String> errors;    // optional list of field/ constraint errors

}
