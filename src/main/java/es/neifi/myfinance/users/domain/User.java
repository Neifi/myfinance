package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.AggregateRoot;

import java.util.Objects;


public class User extends AggregateRoot {

    private UserID id;
    private UserName username;
    private Email email;

    private User(UserID id, UserName username, Email email) {
        this.id = id;
        this.username = username;
        this.email = email;


    }

    public static User createUser(UserID id, UserName username, Email email){
        User user = new User(id, username, email);
        UserRegisteredDomainEvent userRegisteredDomainEvent = new UserRegisteredDomainEvent(id);
        user.record(userRegisteredDomainEvent);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.value().equalsIgnoreCase(user.id.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.value());
    }

    public UserID id() {
        return id;
    }

    public UserName username() {
        return username;
    }

    public Email email() {
        return email;
    }
}
