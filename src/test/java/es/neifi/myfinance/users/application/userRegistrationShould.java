package es.neifi.myfinance.users.application;

import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.application.register.RegisterUserCommand;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class userRegistrationShould {


    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private EventBus eventBus = Mockito.mock(EventBus.class);
    private UserRegistrator userRegistrator = new UserRegistrator(userRepository,eventBus);
    @Test
    public void should_register_user_successfully_and_emit_user_registered_event(){
        String userId = "4c78e316-2ccd-4f12-a8b7-aa2a87c9adcf";
        userRegistrator.register(new RegisterUserCommand(userId,"some-name","some-email@email.com"));

        User user = User.createUser(new UserID(userId),new UserName("some-name"),new Email("some-email@email.com"));

        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        List<DomainEvent<?>> events = user.pullEvents();
        Mockito.verify(eventBus,Mockito.times(1)).publish(any());

    }


}