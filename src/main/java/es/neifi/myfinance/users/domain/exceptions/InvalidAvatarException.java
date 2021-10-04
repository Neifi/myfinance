package es.neifi.myfinance.users.domain.exceptions;

public class InvalidAvatarException extends RuntimeException {
    public InvalidAvatarException(String message) {
        super(message);
    }
}
