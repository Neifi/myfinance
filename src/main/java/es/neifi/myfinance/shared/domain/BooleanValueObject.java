package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BooleanValueObject {

    private boolean value;

    public BooleanValueObject(boolean value) {
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }
}
