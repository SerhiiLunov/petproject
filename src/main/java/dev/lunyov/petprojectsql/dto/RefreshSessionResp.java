package dev.lunyov.petprojectsql.dto;

import dev.lunyov.petprojectsql.entity.Session;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshSessionResp {
    private Session session;

    public RefreshSessionResp() {
    }

    public RefreshSessionResp(Session session) {
        this.session = session;
    }
}
