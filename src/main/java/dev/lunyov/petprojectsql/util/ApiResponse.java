package dev.lunyov.petprojectsql.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String status;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }
}