package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
public class PasswordDto {

    @NotBlank(message = "New password cannot be empty")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be empty")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String confirmPassword;
}