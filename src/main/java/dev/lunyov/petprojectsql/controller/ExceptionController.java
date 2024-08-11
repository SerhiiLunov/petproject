package dev.lunyov.petprojectsql.controller;

import dev.lunyov.petprojectsql.util.ApiResponse;
import dev.lunyov.petprojectsql.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static dev.lunyov.petprojectsql.util.Status.*;

@RestController
@RequestMapping("/api")
public class ExceptionController {

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<String>> getSuccess() {
        ApiResponse<String> response = new ApiResponse<>(SUCCESS, "Successful response data");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/request-error")
    public ResponseEntity<ApiResponse<ErrorResponse>> getRequestError() {
        ErrorResponse errorResponse = new ErrorResponse("400", "Invalid request data", new HashMap<>());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(REQUEST_ERROR, errorResponse);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/server-error")
    public ResponseEntity<ApiResponse<ErrorResponse>> getServerError() {
        ErrorResponse errorResponse = new ErrorResponse("500", "Internal server error", new HashMap<>());
        ApiResponse<ErrorResponse> response = new ApiResponse<>(SERVER_ERROR, errorResponse);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
