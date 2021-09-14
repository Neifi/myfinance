package es.neifi.myfinance.users.application.register;

import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;

public class UserRegistrator{

    private final UserRepository userRepository;
    private final EventBus eventBus;

    public UserRegistrator(UserRepository userRepository, EventBus eventBus) {
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }

    public void register(RegisterUserCommand request) {
        User newUser = User.createUser(new UserID(request.id()),new UserName(request.name()),new Email(request.email()));
        this.userRepository.save(newUser);
        this.eventBus.publish(newUser.pullEvents());
    }
}
