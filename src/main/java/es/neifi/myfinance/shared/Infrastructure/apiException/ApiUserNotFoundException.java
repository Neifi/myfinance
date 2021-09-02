package es.neifi.myfinance.shared.Infrastructure.apiException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiUserNotFoundException extends ResponseStatusException {
    public ApiUserNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND,"User not found with id: "+userId);

    }
}
