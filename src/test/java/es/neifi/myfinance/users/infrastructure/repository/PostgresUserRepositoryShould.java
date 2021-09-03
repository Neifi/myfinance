package es.neifi.myfinance.users.infrastructure.repository;

import es.neifi.myfinance.MyfinanceApplication;
import es.neifi.myfinance.shared.Infrastructure.IntegrationTestBase;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyfinanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostgresUserRepositoryShould extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryForTest userRepositoryForTest;


    @Test
    void save_user_in_db() {
        String userId = "061bddf7-dac7-4a2d-b7ab-e040bbcfd339";
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
        String userId = "061bddf7-dac7-4a2d-b7ab-e040bbcfd339";
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