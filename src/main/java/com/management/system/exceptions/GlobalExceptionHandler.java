package com.management.system.exceptions;

import com.management.system.exceptions.Dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFound(NotFoundException ex, HttpServletRequest request) {
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setTimestamp(OffsetDateTime.now());
        err.setStatus(404);
        err.setError("Not Found");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        err.setFieldErrors(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> badRequest(BadRequestException ex, HttpServletRequest request) {
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setTimestamp(OffsetDateTime.now());
        err.setStatus(400);
        err.setError("Bad Request");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        err.setFieldErrors(null);

        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> conflict(ConflictException ex, HttpServletRequest request) {
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setTimestamp(OffsetDateTime.now());
        err.setStatus(409);
        err.setError("Conflict");
        err.setMessage(ex.getMessage());
        err.setPath(request.getRequestURI());
        err.setFieldErrors(null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> exception(Exception ex, HttpServletRequest request) {
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setTimestamp(OffsetDateTime.now());
        err.setStatus(500);
//        err.setError(ex.getMessage());
        err.setError("Internal Server Error");
        err.setMessage("Something went wrong");
        err.setPath(request.getRequestURI());
        err.setFieldErrors(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());

        }
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setTimestamp(OffsetDateTime.now());
        err.setStatus(400);
        err.setError("Validation Failed");
        err.setMessage("One or more fields are invalid");
        err.setPath(request.getRequestURI());
        err.setFieldErrors(errors);

        return ResponseEntity.badRequest().body(err);
    }


}
