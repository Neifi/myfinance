package es.neifi.myfinance.users.application.register;

import es.neifi.myfinance.users.domain.Email;
import es.neifi.myfinance.users.domain.User;
import es.neifi.myfinance.users.domain.UserID;
import es.neifi.myfinance.users.domain.UserName;
import es.neifi.myfinance.users.domain.UserRepository;

public class UserRegistrator{

    private final UserRepository userRepository;

    public UserRegistrator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void register(RegisterUserCommand request) {
        User toRegister = new User(new UserID(request.id()),new UserName(request.name()),new Email(request.email()));
        this.userRepository.save(toRegister);
    }
}
