package es.neifi.myfinance.shared.domain.baseValueObject;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;

public class BaseValueObject <Type>{
    private Type value;
    public BaseValueObject(Type value) {
        if (value == null){
            throw new EmptyValueException("Value must not be null");
        }
        this.value = value;
    }

    public Type value() {
        return value;
    }
}
