package es.neifi.myfinance.shared.domain;

import es.neifi.myfinance.users.application.UserNotFoundException;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> find(String userId){
        Optional<User> user = userRepository.searchById(userId);
        if (user.isEmpty()){
            throw new UserNotFoundException(userId);
        }
        return user;
    }
}