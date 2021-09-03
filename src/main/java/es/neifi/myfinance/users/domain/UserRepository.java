package es.neifi.myfinance.users.domain;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepository {
    void save(User user);
    Optional<User> searchById(String id);
}
