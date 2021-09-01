package es.neifi.myfinance.users.application;

import es.neifi.myfinance.users.application.register.RegisterUserCommand;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

class userRegistrationShould {


    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private UserRegistrator userRegistrator = new UserRegistrator(userRepository);

    @Test
    public void should_register_user_successfully(){
        userRegistrator.register(new RegisterUserCommand("F82861E5-176A-469E-8522-2339AB6C98E6","some-name","some-email@email.com"));

        User user = new User(new UserID("F82861E5-176A-469E-8522-2339AB6C98E6"),new UserName("some-name"),new Email("some-email@email.com"));
        Mockito.verify(userRepository,Mockito.times(1)).save(user);

    }
}