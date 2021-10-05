package es.neifi.myfinance.shared.domain.baseValueObject;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;

public class BaseValueObject <T>{
    private final T value;
    public BaseValueObject(T value) {
        if (value == null){
            throw new EmptyValueException("Value must not be null");
        }
        this.value = value;
    }

    public T value() {
        return value;
    }
}
