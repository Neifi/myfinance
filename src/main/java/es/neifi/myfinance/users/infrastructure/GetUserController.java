package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.shared.Infrastructure.apiException.ApiUserNotFoundError;
import es.neifi.myfinance.users.application.exceptions.UserNotFoundException;
import es.neifi.myfinance.users.application.find.UserFinder;
import es.neifi.myfinance.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GetUserController {

    private final UserFinder userFinder;

    public GetUserController(UserFinder userFinder) {
        this.userFinder = userFinder;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> registerUser(@PathVariable String userId) {
        try {
            Optional<User> userById = this.userFinder.findById(userId);

            Response response = new Response(
                    userById.get().id().value(),
                    userById.get().username().value(),
                    userById.get().email().value(),
                    userById.get().avatar().value()
            );

            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiUserNotFoundError(userId));
        }

    }

    private static final class Response {
        private String userId;
        private String userName;
        private String email;
        private String avatar;

        public Response(String userId, String userName, String email, String avatar) {
            this.userId = userId;
            this.userName = userName;
            this.email = email;
            this.avatar = avatar;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getAvatar(){
            return avatar;
        }
    }

}
