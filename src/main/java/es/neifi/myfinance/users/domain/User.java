package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.AggregateRoot;

import java.util.Objects;


public class User extends AggregateRoot {

    private UserID id;
    private UserName username;
    private Email email;
    private Avatar avatar;

    private User(UserID id, UserName username, Email email, Avatar avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;

    }

    public static User createUser(UserID id, UserName username, Email email, Avatar avatar) {
        User user = new User(id, username, email, avatar);
        UserRegisteredDomainEvent userRegisteredDomainEvent = new UserRegisteredDomainEvent(id);
        user.capture(userRegisteredDomainEvent);
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

    public Avatar avatar() {
        return avatar;
    }
}
