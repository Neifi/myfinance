package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.shared.domain.StringValueObject;
import lombok.EqualsAndHashCode;

public class Category extends StringValueObject {
    public Category(String value) {
        super(value);
    }
}

