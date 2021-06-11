package es.neifi.myfinance.users.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode

public class User {

    private UserID id;
    private UserName username;
    private Email email;

    public User(UserID id, UserName username, Email email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }


}
