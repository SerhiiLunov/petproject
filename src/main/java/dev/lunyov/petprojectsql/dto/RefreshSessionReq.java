package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshSessionReq {
    private String token;

    public RefreshSessionReq() {
    }

    public RefreshSessionReq(String token) {
        this.token = token;
    }
}
