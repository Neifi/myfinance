package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.StringValueObject;
import es.neifi.myfinance.shared.domain.exception.EmptyValueException;


public class Name extends StringValueObject {
    public Name(String value) {
        super(value);
        if (value == null || value.isBlank()){
            throw new EmptyValueException("Name cannot be empty");
        }
    }
}
