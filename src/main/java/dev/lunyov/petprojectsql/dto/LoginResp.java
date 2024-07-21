package dev.lunyov.petprojectsql.dto;

import dev.lunyov.petprojectsql.entity.Session;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResp {
    private Session session;

    public LoginResp() {
    }

    public LoginResp(Session session) {
        this.session = session;
    }
}
