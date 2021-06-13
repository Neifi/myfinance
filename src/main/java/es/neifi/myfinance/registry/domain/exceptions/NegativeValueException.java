package es.neifi.myfinance.registry.domain.exceptions;

public class NegativeValueException extends IllegalArgumentException{
    public NegativeValueException(String message) {
        super(message);

    }
}
