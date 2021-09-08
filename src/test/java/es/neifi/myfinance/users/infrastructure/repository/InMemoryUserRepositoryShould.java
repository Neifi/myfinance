package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.users.domain.*;
import es.neifi.myfinance.users.infrastructure.repository.InMemoryUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryUserRepositoryShould {


    @Test
    void save_user() {
        UserRepository userRepository = new InMemoryUserRepository();
        User user = new User(new UserID("6657B41E-FBA8-486C-9F4C-474B351514D1"),new UserName("some-name"),new Email("some-email@email.com"));
        userRepository.save(user);
    }

    @Test
    void return_an_existing_user() {
        UserRepository userRepository = new InMemoryUserRepository();
        User expectedUser = new User(new UserID("6657B41E-FBA8-486C-9F4C-474B351514D1"),new UserName("some-name"),new Email("some-email@mail.com"));
        userRepository.save(expectedUser);

        Optional<User> user = userRepository.searchById("6657B41E-FBA8-486C-9F4C-474B351514D1".toLowerCase());

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(expectedUser,user.get());
    }

    @Test
    void not_return_non_existing_course(){
        UserRepository userRepository = new InMemoryUserRepository();
        assertFalse(userRepository.searchById("some_id").isPresent());
    }
}