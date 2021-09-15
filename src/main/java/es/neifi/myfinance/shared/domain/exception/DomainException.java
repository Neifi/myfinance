package es.neifi.myfinance.shared.domain.exception;

public class DomainException extends IllegalArgumentException{

    public DomainException() {
        super();
    }

    public DomainException(String s) {
        super(s);
    }
}
