package es.neifi.myfinance.users.infrastructure;

import es.neifi.myfinance.shared.Infrastructure.apiException.ApiUserNotFoundError;
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
        Optional<User> userById = this.userFinder.findUserById(userId);
        if (userById.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiUserNotFoundError(userId));
        }
        Response response = new Response(
                userById.get().getId().value(),
                userById.get().getUsername().value(),
                userById.get().getEmail().value()
        );

        return ResponseEntity.ok(response);
    }

    private static final class Response {
        private String userId;
        private String userName;
        private String email;

        public Response(String userId, String userName, String email) {
            this.userId = userId;
            this.userName = userName;
            this.email = email;
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
    }

}
