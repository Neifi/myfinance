package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StringValueObject {
    private String value;

    public StringValueObject(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
