package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AddPartnerReq {
    @NotEmpty
    private String email;
}
