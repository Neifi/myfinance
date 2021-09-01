package es.neifi.myfinance.shared.Infrastructure.apiException;

import es.neifi.myfinance.users.application.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiUserNotFoundException extends ResponseStatusException {
    public ApiUserNotFoundException(HttpStatus status, String message, UserNotFoundException reason) {
        super(status,message,reason);

    }
}
