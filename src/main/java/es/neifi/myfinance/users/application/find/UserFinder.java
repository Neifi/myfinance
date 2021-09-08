package es.neifi.myfinance.users.application.find;

import es.neifi.myfinance.users.application.UserNotFoundException;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;

import java.util.Optional;

public class UserFinder {

    private final UserRepository userRepository;

    public UserFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String userId){
        Optional<User> user = userRepository.searchById(userId);
        if (user.isEmpty()) throw new UserNotFoundException(userId);

        return user;
    }
}