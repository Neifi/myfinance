package es.neifi.myfinance.expenses.domain.exceptions;

public class ExpenseCostException extends IllegalArgumentException{
    public ExpenseCostException(String message) {
        super(message);

    }
}
