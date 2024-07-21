package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class ResetPasswordResp {
    private String message;

    public ResetPasswordResp() {
    }

    public ResetPasswordResp(String message) {
        this.message = message;
    }
}
