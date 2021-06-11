package es.neifi.myfinance.users.application.register;

import es.neifi.myfinance.users.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrator{

    @Autowired
    private UserRepository userRepository;

    public UserRegistrator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void register(RegisterUserRequest request) {
        User toRegister = new User(new UserID(request.id()),new UserName(request.name()),new Email(request.email()));
        this.userRepository.save(toRegister);
    }
}
