package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        Optional<User> user = userRepository.search("6657B41E-FBA8-486C-9F4C-474B351514D1".toLowerCase());

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(expectedUser,user.get());
    }

    @Test
    void not_return_non_existing_course(){
        UserRepository userRepository = new InMemoryUserRepository();
        assertFalse(userRepository.search("some_id").isPresent());
    }
}