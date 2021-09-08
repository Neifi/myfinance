package es.neifi.myfinance.shared.domain;

import es.neifi.myfinance.shared.domain.exception.EmptyValueException;

public class BaseValueObject {
    public BaseValueObject(Object o) {
        if (o == null){
            throw new EmptyValueException("Value must not be null");
        }
    }
}
