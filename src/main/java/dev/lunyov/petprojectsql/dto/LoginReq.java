package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginReq {
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Incorrect email format")
    private String email;
    @NotEmpty(message = "Password can't be empty")
    private String password;

    public LoginReq() {
    }

    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
