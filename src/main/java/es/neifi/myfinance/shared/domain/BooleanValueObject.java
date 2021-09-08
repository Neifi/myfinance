package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BooleanValueObject extends BaseValueObject{

    private boolean value;

    public BooleanValueObject(boolean value) {
        super(value);
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }
}
