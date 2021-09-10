package es.neifi.myfinance.users.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class User {

    private UserID id;
    private UserName username;
    private Email email;

    public User(UserID id, UserName username, Email email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.value().equalsIgnoreCase(user.id.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
