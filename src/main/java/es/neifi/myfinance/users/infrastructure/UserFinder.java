package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;

import java.util.Optional;

public class UserFinder {
    private UserRepository userRepository;

    public UserFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(String userId) {
        return Optional.empty();
    }
}
