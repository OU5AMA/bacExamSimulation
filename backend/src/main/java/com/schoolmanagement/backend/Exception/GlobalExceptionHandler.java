package com.schoolmanagement.backend.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 400: @Valid body failed (e.g., invalid email, blank fields)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request) {

        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                errors.add(fe.getField() + ": " + fe.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(ge ->
                errors.add(ge.getObjectName() + ": " + ge.getDefaultMessage()));

        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(HttpStatus.valueOf(status.value()).getReasonPhrase())
                .message("Validation failed")
                .path(getPathFromWebRequest(request))
                .errors(errors)
                .build();

        return new ResponseEntity<>(body, headers, status);
    }

    // 400: invalid/malformed JSON, missing body, wrong enum, etc.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request) {

        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(HttpStatus.valueOf(status.value()).getReasonPhrase())
                .message("Malformed JSON or unreadable request body")
                .path(getPathFromWebRequest(request))
                .errors(List.of(ex.getMostSpecificCause() != null
                        ? ex.getMostSpecificCause().getMessage()
                        : ex.getMessage()))
                .build();

        return new ResponseEntity<>(body, headers, status);
    }

    // 400: @Validated on params/path variables (e.g., @Positive @Min)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {

        List<String> errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Constraint violations")
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    // 400: wrong type in path variable (e.g., /users/abc where Long expected)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String detail = ex.getName() + " should be of type " +
                (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "required type");

        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Argument type mismatch")
                .path(request.getRequestURI())
                .errors(List.of(detail))
                .build();

        return ResponseEntity.badRequest().body(body);
    }

    // 409: unique constraint, e.g., duplicate email (users.email is unique)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message("Data integrity violation")
                .path(request.getRequestURI())
                .errors(List.of(ex.getMostSpecificCause() != null
                        ? ex.getMostSpecificCause().getMessage()
                        : ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 500: fallback for any uncaught exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest request) {
        ApiError body = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected error")
                .path(request.getRequestURI())
                .errors(List.of(ex.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // Helper to get path in overridden handlers (WebRequest doesn't expose URI directly)
    private String getPathFromWebRequest(org.springframework.web.context.request.WebRequest request) {
        String desc = request.getDescription(false); // like "uri=/api/v1/users"
        return desc != null && desc.startsWith("uri=") ? desc.substring(4) : desc;
    }
}
