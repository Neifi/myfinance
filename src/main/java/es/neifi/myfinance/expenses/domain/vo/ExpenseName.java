package es.neifi.myfinance.expenses.domain.vo;

import es.neifi.myfinance.shared.domain.StringValueObject;
import lombok.EqualsAndHashCode;


public class ExpenseName extends StringValueObject {
    public ExpenseName(String value) {
        super(value);
    }
}
