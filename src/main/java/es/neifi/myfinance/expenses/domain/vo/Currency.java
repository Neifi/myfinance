package es.neifi.myfinance.expenses.domain.vo;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Currency {
    private String value;

    public Currency(String value) {
        java.util.Currency.getInstance(value);
        this.value = value;
    }

}
