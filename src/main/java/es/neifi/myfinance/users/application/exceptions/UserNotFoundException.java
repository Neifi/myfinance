package es.neifi.myfinance.users.application.exceptions;

import es.neifi.myfinance.shared.application.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String id) {
        super("User not found with ID: " + id);
    }
}
