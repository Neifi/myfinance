package es.neifi.myfinance.users.application;

import es.neifi.myfinance.shared.domain.Service;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> search(String id){
        return userRepository.search(id);
    }

}
