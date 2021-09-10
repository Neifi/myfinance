package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;


class InMemoryUserRepositoryShould {


    @Test
    void save_user() {
        UserRepository userRepository = new InMemoryUserRepository();
        User user = new User(new UserID("54bd749a-cc9a-4a68-a903-1c3c6ae77089"),new UserName("some-name"),new Email("some-email@email.com"));
        userRepository.save(user);
    }

    @Test
    void return_an_existing_user() {
        UserRepository userRepository = new InMemoryUserRepository();
        String userId = "54bd749a-cc9a-4a68-a903-1c3c6ae77089";
        User expectedUser = new User(new UserID(userId),new UserName("some-name"),new Email("some-email@mail.com"));
        userRepository.save(expectedUser);

        Optional<User> user = userRepository.searchById(userId);

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(expectedUser,user.get());
    }

    @Test
    void not_return_non_existing_course(){
        UserRepository userRepository = new InMemoryUserRepository();
        assertFalse(userRepository.searchById("some_id").isPresent());
    }
}