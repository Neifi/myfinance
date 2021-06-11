package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.users.application.register.RegisterUserRequest;
import es.neifi.myfinance.users.application.register.UserRegistrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostRegisterUserController  {


    private UserRegistrator userRegistrator;

    public PostRegisterUserController(UserRegistrator userRegistrator) {
        this.userRegistrator = userRegistrator;
    }

    @PostMapping("/users/{id}")
    public ResponseEntity registerUser(@PathVariable String id, @RequestBody Request request){
        userRegistrator.register(new RegisterUserRequest(id,request.name(),request.email()));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private static final class Request{
        private String name;
        private String email;

        public String name() {
            return name;
        }

        public String email() {
            return email;
        }
    }
}
