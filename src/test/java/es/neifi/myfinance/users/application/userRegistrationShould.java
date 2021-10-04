package es.neifi.myfinance.users.application;

import es.neifi.myfinance.shared.Infrastructure.cloud.CloudStorageService;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.application.register.RegisterUserCommand;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import es.neifi.myfinance.users.domain.Avatar;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class userRegistrationShould {


    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CloudStorageService cloudStorageService = Mockito.mock(CloudStorageService.class);
    private final EventBus eventBus = Mockito.mock(EventBus.class);
    private final UserRegistrator userRegistrator = new UserRegistrator(userRepository, eventBus, cloudStorageService);

    @Test
    public void should_register_user_successfully_and_emit_user_registered_event() {

        String userId = "4c78e316-2ccd-4f12-a8b7-aa2a87c9adcf";
        MultipartFile avatar = new MockMultipartFile("avatar.png", "some-bytes".getBytes());
        String avatarUrl = "https://somedomain.cloudfront.net/3d18e316-2ccd-5f12-a8b7-aa2a87c9adfc";

        Mockito.when(cloudStorageService.retrieve(any(String.class))).thenReturn(avatarUrl);

        userRegistrator.register(new RegisterUserCommand(userId, "some-name", "some-email@email.com", avatar));

        User user = User.createUser(new UserID(userId), new UserName("some-name"), new Email("some-email@email.com"), new Avatar(avatarUrl));


        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        List<DomainEvent<?>> events = user.pullEvents();
        Mockito.verify(eventBus, Mockito.times(1)).publish(any());

    }

}