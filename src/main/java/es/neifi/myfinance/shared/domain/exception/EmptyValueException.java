package es.neifi.myfinance.shared.domain.exception;

public class EmptyValueException extends DomainException {
    public EmptyValueException(String msg) {
        super(msg);
    }
}
