package dev.lunyov.petprojectsql.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
