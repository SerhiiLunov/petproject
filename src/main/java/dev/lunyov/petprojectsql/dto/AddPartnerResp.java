package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPartnerResp {
    private String message;

    public AddPartnerResp() {
    }

    public AddPartnerResp(String message) {
        this.message = message;
    }
}