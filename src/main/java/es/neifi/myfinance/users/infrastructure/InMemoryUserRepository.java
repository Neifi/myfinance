package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public final class InMemoryUserRepository implements UserRepository {

    private HashMap<String, User> users = new HashMap<>();

    @Override
    public void save(User User) {
        users.put(User.getId().value(), User);
    }

    @Override
    public Optional<User> search(String id) {
        return Optional.ofNullable(users.get(id));
    }

}