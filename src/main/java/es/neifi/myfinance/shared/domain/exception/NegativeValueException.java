package es.neifi.myfinance.shared.domain.exception;

public class NegativeValueException extends DomainException{
    public NegativeValueException(String message) {
        super(message);

    }
}
