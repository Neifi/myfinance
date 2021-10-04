package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.application.register.RegisterUserCommand;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostRegisterUserController  {


    private final UserRegistrator userRegistrator;

    public PostRegisterUserController(UserRegistrator userRegistrator) {
        this.userRegistrator = userRegistrator;
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<HttpStatus> registerUser(@PathVariable String id, @ModelAttribute RegisterUserCommand request){
        userRegistrator.register(new RegisterUserCommand(id,request.name(),request.email(),request.avatar()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
