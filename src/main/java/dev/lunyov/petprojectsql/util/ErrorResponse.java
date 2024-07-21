package dev.lunyov.petprojectsql.util;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String errorDescription;
    private Map<String, Object> errorData;

    public ErrorResponse() {}

    public ErrorResponse(String errorCode, String errorDescription, Map<String, Object> errorData) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorData = errorData;
    }
}
