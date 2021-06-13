package es.neifi.myfinance.expenses.domain.exceptions;

public class NegativeValueException extends IllegalArgumentException{
    public NegativeValueException(String message) {
        super(message);

    }
}
