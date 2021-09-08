package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class StringValueObject extends BaseValueObject{
    private String value;

    public StringValueObject(String value) {
        super(value);
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
