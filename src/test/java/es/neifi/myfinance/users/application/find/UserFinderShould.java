package es.neifi.myfinance.users.application.find;

import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserFinderShould {


    private UserRepository userRepository = mock(UserRepository.class);

    private UserFinder userFinder = new UserFinder(userRepository);

    @Test
    void throw_exception_when_user_is_not_found() {
        String userId = "3aa80613-0a27-46d8-8367-d91c237967a4";
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userFinder.findById(userId);
        });

        assertThat(exception).hasMessage("User not found with ID: " + userId);
    }

    @Test
    void find_user_by_id() {
        String userId = "3aa80613-0a27-46d8-8367-d91c237967a4";
        Optional user = Optional.of(User.createUser(
                        new UserID(userId),
                        new UserName("username"),
                        new Email("email@mail.com")
                )
        );
        when(userRepository.searchById(userId)).thenReturn(user);

        Optional<User> expectedUser = userFinder.findById(userId);

        assertThat(expectedUser).isNotEmpty();
        assertThat(expectedUser).isEqualTo(user);
    }
}