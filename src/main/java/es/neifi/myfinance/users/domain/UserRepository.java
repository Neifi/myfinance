package es.neifi.myfinance.users.domain;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> search(String id);
}
