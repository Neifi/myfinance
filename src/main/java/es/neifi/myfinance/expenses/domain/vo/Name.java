package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.shared.domain.StringValueObject;
import lombok.EqualsAndHashCode;


public class Name extends StringValueObject {
    public Name(String value) {
        super(value);
    }
}
