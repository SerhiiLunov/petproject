package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CreateUserByEmailReq {
    @Email
    private String email;

    @Size(max = 255)
    private String description;
}
