package dev.lunyov.petprojectsql.dto;

import dev.lunyov.petprojectsql.util.ApiResponse;
import dev.lunyov.petprojectsql.util.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordResp extends ApiResponse {
    private String message;

    public ResetPasswordResp() {
    }

    public ResetPasswordResp(String message) {
        this.message = message;
    }

    public ResetPasswordResp(Status status, Object data, String message) {
        super(status, data);
        this.message = message;
    }
}
