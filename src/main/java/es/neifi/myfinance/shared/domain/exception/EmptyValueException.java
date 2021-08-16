package es.neifi.myfinance.shared.domain.exception;

public class EmptyValueException extends RuntimeException {
    public EmptyValueException(String msg) {
        super(msg);
    }
}
