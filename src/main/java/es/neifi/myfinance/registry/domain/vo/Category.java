package es.neifi.myfinance.registry.domain.vo;

import es.neifi.myfinance.shared.domain.StringValueObject;
import es.neifi.myfinance.shared.domain.exception.EmptyValueException;

public class Category extends StringValueObject {
    public Category(String value) {
        super(value);
        if (value == null || value.isBlank()){
            throw new EmptyValueException("Category cannot be empty");
        }
    }
}

