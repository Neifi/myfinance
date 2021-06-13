package es.neifi.myfinance.expenses.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Currency {
    private String value;

    public Currency(String value) {
        java.util.Currency.getInstance(value);
        this.value = value;
    }

}
