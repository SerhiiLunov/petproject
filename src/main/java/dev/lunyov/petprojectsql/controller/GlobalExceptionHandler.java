package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.util.ApiResponse;
import dev.lunyov.petprojectsql.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

import static dev.lunyov.petprojectsql.util.Status.REQUEST_ERROR;
import static dev.lunyov.petprojectsql.util.Status.SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleServerException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("500", ex.getMessage(), new HashMap<>());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(SERVER_ERROR, errorResponse);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequestException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage(), new HashMap<>());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(REQUEST_ERROR, errorResponse);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}