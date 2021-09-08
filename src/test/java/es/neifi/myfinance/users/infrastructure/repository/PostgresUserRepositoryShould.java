package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.shared.Infrastructure.IntegrationTestBase;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

class PostgresUserRepositoryShould extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryForTest userRepositoryForTest;


    @Test
    void save_user_in_db() {
        String userId = UUID.randomUUID().toString();
        User user = new User(
                new UserID(userId),
                new UserName("username"),
                new Email("email@mail.com")
        );

        userRepository.save(user);
        User actualUser = userRepositoryForTest.select(userId);

        Assertions.assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void get_existent_user_from_db() {
        String userId = UUID.randomUUID().toString();
        Optional<User> user = Optional.of(new User(
                        new UserID(userId),
                        new UserName("username"),
                        new Email("email@mail.com")
                )
        );
        userRepository.save(user.get());
        Optional<User> actualUser = userRepository.searchById(userId);

        Assertions.assertThat(actualUser).isEqualTo(user);
    }
}