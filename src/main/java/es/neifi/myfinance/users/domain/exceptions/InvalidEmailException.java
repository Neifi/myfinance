package es.neifi.myfinance.users.domain.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException(String value) {
        super("email "+value+" is not a valid email");
    }
}
