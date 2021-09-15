package es.neifi.myfinance.users.domain.exceptions;

import es.neifi.myfinance.shared.domain.exception.DomainException;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String value) {
        super("email "+value+" is not a valid email");
    }
}
