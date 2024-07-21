package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ResetPasswordReq {
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Incorrect email format")
    private String email;

    public ResetPasswordReq() {
    }

    public ResetPasswordReq(String email) {
        this.email = email;
    }
}