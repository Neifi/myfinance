package es.neifi.myfinance.shared.domain;

import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> search(String id){
        return userRepository.search(id);
    }

}
